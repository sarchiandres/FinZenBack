package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.models.DTO.UsuarioDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.Services.UsuarioServices;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finzen/usuario")
public class UsuarioController {

    private final UsuarioServices usuarioServices;

    public UsuarioController(UsuarioServices usuarioServices) {
        this.usuarioServices = usuarioServices;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')") // Solo ADMIN puede crear usuarios
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody UsuarioDto usuario) {
        Usuario createdUsuario = usuarioServices.createUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
    }

    @GetMapping(value = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // USER o ADMIN pueden consultar
    public ResponseEntity<Usuario> getUsuarioDocumento(@PathVariable Long documento) {
        return ResponseEntity.ok(usuarioServices.getUsuarioDocumento(documento));
    }

    @PutMapping(value = "/{documento}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.correo == #usuario.correo") // ADMIN o el propio usuario
    public ResponseEntity<Usuario> updateUsuario(
            @PathVariable Long documento,
            @Valid @RequestBody UsuarioDto usuario) {
        return ResponseEntity.ok(usuarioServices.updateUsuario(documento, usuario));
    }

    @DeleteMapping(value = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')") // Solo ADMIN puede eliminar
    public ResponseEntity<String> deleteUsuario(@PathVariable Long documento) {
        return ResponseEntity.ok(usuarioServices.deleteUsuario(documento));
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Usuario> getCurrentUsuario(Authentication authentication) {
        String correo = authentication.name(); // El correo es el "username" en JWT
        return ResponseEntity.ok(usuarioServices.getUsuarioByCorreo(correo));
    }
}