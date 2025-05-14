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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
                usuario.getNombreUsuario(), // Devolver nombreUsuario en lugar de contrasena
                usuario.getCorreo(),
                role));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (usuarioRepository.existsByNombreUsuario(signUpRequest.getNombreUsuario())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: El nombre de usuario ya está en uso!"));
        }

        if (usuarioRepository.existsByCorreo(signUpRequest.getCorreo())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: El correo ya está en uso!"));
        }

        if (usuarioRepository.findByNumeroDocumento(signUpRequest.getNumeroDocumento()).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: El número de documento ya está en uso!"));
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(signUpRequest.getNombre());
        usuario.setCorreo(signUpRequest.getCorreo());
        usuario.setContrasena(encoder.encode(signUpRequest.getContrasena()));
        usuario.setNumeroDocumento(signUpRequest.getNumeroDocumento());
        usuario.setNombreUsuario(signUpRequest.getNombreUsuario());
        usuario.setPaisResidencia(signUpRequest.getPaisResidencia());
        usuario.setIngresoMensual(signUpRequest.getIngresoMensual() != null ? signUpRequest.getIngresoMensual() : 0L);
        usuario.setMetaActual(signUpRequest.getMetaActual() != null ? signUpRequest.getMetaActual() : true);

        try {
            usuario.setTipoDocumento(Usuario.TipoDocumentoEnum.valueOf(signUpRequest.getTipoDocumento()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Tipo de documento inválido"));
        }

        if (signUpRequest.getTipoPersona() != null) {
            try {
                usuario.setTipoPersona(Usuario.TipoPersonaEnum.valueOf(signUpRequest.getTipoPersona()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Tipo de persona inválido"));
            }
        } else {
            usuario.setTipoPersona(Usuario.TipoPersonaEnum.personalizado);
        }

        TipoUsuario tipoUsuario = tipoUsuarioRepository.findByNombre("USUARIO")
                .orElseThrow(() -> new RuntimeException("El tipo de usuario 'USUARIO' no existe"));
        usuario.setTipoUsuario(tipoUsuario);

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado con éxito!"));
    }
}