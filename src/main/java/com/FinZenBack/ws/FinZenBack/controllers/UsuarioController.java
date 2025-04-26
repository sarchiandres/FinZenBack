    package com.FinZenBack.ws.FinZenBack.controllers;

    import com.FinZenBack.ws.FinZenBack.Services.UsuarioServices;
    import com.FinZenBack.ws.FinZenBack.models.Usuario;
    import jakarta.validation.Valid;
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

        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
            return ResponseEntity.ok(usuarioServices.createUsuario(usuario));
        }

        @GetMapping("/{documento}")
        public ResponseEntity<Usuario> getUsuarioDocumento(@PathVariable Long documento) {
            return ResponseEntity.ok(usuarioServices.getUsuarioDocumento(documento));
        }

        @PutMapping("/{documento}")
        public ResponseEntity<Usuario> updateUsuario(
                @PathVariable Long documento,
                @Valid @RequestBody Usuario usuario) {
            return ResponseEntity.ok(usuarioServices.updateUsuario(documento, usuario));
        }

        @DeleteMapping("/{documento}")
        public ResponseEntity<String> deleteUsuario(@PathVariable Long documento) {
            return ResponseEntity.ok(usuarioServices.deleteUsuario(documento));
        }
    }