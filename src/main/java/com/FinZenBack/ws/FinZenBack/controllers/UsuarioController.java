package com.FinZenBack.ws.FinZenBack.controllers;


import com.FinZenBack.ws.FinZenBack.payload.FinZenException;
import com.FinZenBack.ws.FinZenBack.models.DTO.UsuarioDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.Services.UsuarioServices;
import com.FinZenBack.ws.FinZenBack.payload.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finzen/usuario")
public class UsuarioController {

    private final UsuarioServices usuarioServices;

    public UsuarioController(UsuarioServices usuarioServices) {
        this.usuarioServices = usuarioServices;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUsuario(@Valid @RequestBody UsuarioDto usuario) {
        try {
            Usuario createdUsuario = usuarioServices.createUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MessageResponse("Usuario creado con éxito!", createdUsuario));
        } catch (FinZenException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping(value = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUsuarioDocumento(@PathVariable Long documento) {
        try {
            Usuario usuario = usuarioServices.getUsuarioDocumento(documento);
            return ResponseEntity.ok(new MessageResponse("Usuario encontrado", usuario));
        } catch (FinZenException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping(value = "/{documento}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.correo == #usuario.correo")
    public ResponseEntity<?> updateUsuario(@PathVariable Long documento, @Valid @RequestBody UsuarioDto usuario) {
        try {
            Usuario updatedUsuario = usuarioServices.updateUsuario(documento, usuario);
            return ResponseEntity.ok(new MessageResponse("Usuario actualizado con éxito!", updatedUsuario));
        } catch (FinZenException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping(value = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long documento) {
        try {
            String message = usuarioServices.deleteUsuario(documento);
            return ResponseEntity.ok(new MessageResponse(message));
        } catch (FinZenException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCurrentUsuario(Authentication authentication) {
        try {
            String correo = authentication.getName();
            Usuario usuario = usuarioServices.getUsuarioByCorreo(correo);
            return ResponseEntity.ok(new MessageResponse("Usuario actual encontrado", usuario));
        } catch (FinZenException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}