package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.models.DTO.UsuarioDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.Services.UsuarioServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finzen/usuario")
public class UsuarioController {

    private final UsuarioServices usuarioServices;

    public UsuarioController(UsuarioServices usuarioServices) {
        this.usuarioServices = usuarioServices;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody UsuarioDto usuario) {
        Usuario createdUsuario = usuarioServices.createUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
    }

    @GetMapping(value = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> getUsuarioDocumento(@PathVariable Long documento) {
        return ResponseEntity.ok(usuarioServices.getUsuarioDocumento(documento));
    }

    @PutMapping(value = "/{documento}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> updateUsuario(
            @PathVariable Long documento,
            @Valid @RequestBody UsuarioDto usuario) {
        return ResponseEntity.ok(usuarioServices.updateUsuario(documento, usuario));
    }

    @DeleteMapping(value = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUsuario(@PathVariable Long documento) {
        return ResponseEntity.ok(usuarioServices.deleteUsuario(documento));
    }
}