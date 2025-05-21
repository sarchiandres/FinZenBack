package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.models.Entities.TipoUsuario;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.payload.LoginRequest;
import com.FinZenBack.ws.FinZenBack.payload.SignupRequest;
import com.FinZenBack.ws.FinZenBack.payload.response.JwtResponse;
import com.FinZenBack.ws.FinZenBack.payload.response.MessageResponse;
import com.FinZenBack.ws.FinZenBack.repository.TipoUsuarioRepository;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import com.FinZenBack.ws.FinZenBack.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/finzen/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getContrasena()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findByCorreo(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String role = usuario.getTipoUsuario().getNombre();

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                usuario.getIdUsuario(),
                usuario.getNombreUsuario(),
                usuario.getCorreo(),
                role));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        // Depuración avanzada del objeto recibido
        System.out.println("Contenido completo de SignupRequest:");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(objectMapper.writeValueAsString(signUpRequest));
        } catch (Exception e) {
            System.out.println("No se pudo serializar el objeto: " + e.getMessage());
        }

        // Depuración específica del campo role
        System.out.println("Valor de role: " + signUpRequest.getRole());

        // Log the incoming request
        System.out.println("Signup Request: " + signUpRequest.toString());

        // Validate existing user
        if (usuarioRepository.existsByNombreUsuario(signUpRequest.getNombreUsuario())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: El nombre de usuario ya está en uso!"));
        }
        if (usuarioRepository.existsByCorreo(signUpRequest.getCorreo())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: El correo ya está en uso!"));
        }
        if (usuarioRepository.findByNumeroDocumento(signUpRequest.getNumeroDocumento()).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: El número de documento ya está en uso!"));
        }

        // Validate role (con mejor log para debugging)
        String role = signUpRequest.getRole() != null ? signUpRequest.getRole().toUpperCase() : "USUARIO";
        System.out.println("Requested role: " + role + " (original value: " + signUpRequest.getRole() + ")");

        if (!Arrays.asList("USUARIO", "ADMIN").contains(role)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Rol inválido. Debe ser 'USUARIO' o 'ADMIN'"));
        }

        // Verificar los roles existentes en la base de datos
        List<TipoUsuario> tiposExistentes = tipoUsuarioRepository.findAll();
        System.out.println("Roles existentes en la base de datos:");
        for (TipoUsuario tipo : tiposExistentes) {
            System.out.println("ID: " + tipo.getIdTipoUsuario() + ", Nombre: " + tipo.getNombre());
        }

        // Create user
        Usuario usuario = new Usuario();
        usuario.setNombre(signUpRequest.getNombre());
        usuario.setCorreo(signUpRequest.getCorreo());
        usuario.setContrasena(encoder.encode(signUpRequest.getContrasena()));
        usuario.setNumeroDocumento(signUpRequest.getNumeroDocumento());
        usuario.setNombreUsuario(signUpRequest.getNombreUsuario());
        usuario.setPaisResidencia(signUpRequest.getPaisResidencia());
        usuario.setIngresoMensual(signUpRequest.getIngresoMensual() != null ? signUpRequest.getIngresoMensual() : 0L);
        usuario.setMetaActual(signUpRequest.getMetaActual() != null ? signUpRequest.getMetaActual() : true);

        // Set tipoDocumento
        try {
            usuario.setTipoDocumento(Usuario.TipoDocumentoEnum.valueOf(signUpRequest.getTipoDocumento()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Tipo de documento inválido"));
        }

        // Set tipoPersona
        if (signUpRequest.getTipoPersona() != null) {
            try {
                usuario.setTipoPersona(Usuario.TipoPersonaEnum.valueOf(signUpRequest.getTipoPersona()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Tipo de persona inválido"));
            }
        } else {
            usuario.setTipoPersona(Usuario.TipoPersonaEnum.personalizado);
        }

        // Set tipoUsuario based on role
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findByNombre(role)
                .orElseGet(() -> {
                    System.out.println("Role '" + role + "' not found, creating it");
                    TipoUsuario newRole = new TipoUsuario();
                    newRole.setNombre(role);
                    return tipoUsuarioRepository.save(newRole);
                });

        System.out.println("Assigned tipoUsuario: " + tipoUsuario.getNombre() + " (ID: " + tipoUsuario.getIdTipoUsuario() + ")");
        usuario.setTipoUsuario(tipoUsuario);

        Usuario savedUsuario = usuarioRepository.save(usuario);
        System.out.println("Usuario guardado con ID: " + savedUsuario.getIdUsuario() + " y rol: " + savedUsuario.getTipoUsuario().getNombre());

        // Construir una respuesta más detallada para depuración
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", savedUsuario.getIdUsuario());
        responseData.put("nombre", savedUsuario.getNombre());
        responseData.put("correo", savedUsuario.getCorreo());
        responseData.put("nombreUsuario", savedUsuario.getNombreUsuario());
        responseData.put("tipoUsuarioId", savedUsuario.getTipoUsuario().getIdTipoUsuario());
        responseData.put("tipoUsuarioNombre", savedUsuario.getTipoUsuario().getNombre());

        // Devolver una respuesta más detallada
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Usuario registrado con éxito!");
        response.put("data", responseData);

        return ResponseEntity.ok(response);
    }

    // Método para comprobar los roles existentes - útil para debug
    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(tipoUsuarioRepository.findAll());
    }
}