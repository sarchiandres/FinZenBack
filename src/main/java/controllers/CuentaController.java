package controllers;

import models.Cuenta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.CuentaService;
import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {


    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }


    // Obtener todas las cuentas
    @GetMapping
    public ResponseEntity<List<Cuenta>> obtenerTodas() {
        return ResponseEntity.ok(cuentaService.obtenerTodas());
    }

    // Obtener cuenta por ID
    @GetMapping("/{documento}")
    public ResponseEntity<Cuenta> obtenerPorId(@PathVariable int id_cuenta) {
        return cuentaService.obtenerPorId(id_cuenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nueva cuenta
    @PostMapping("/cuentaCreate")
    public ResponseEntity<Cuenta> guardar(@RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(cuentaService.guardar(cuenta));
    }

    // Actualizar una cuenta existente
    @PutMapping("/{documento}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable int id_cuenta, @RequestBody Cuenta cuenta) {
        return cuentaService.obtenerPorId(id_cuenta)
                .map(c -> {
                    cuenta.setId_cuenta(id_cuenta); // Asegura que se actualice la cuenta existente
                    return ResponseEntity.ok(cuentaService.guardar(cuenta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar cuenta por ID
    @DeleteMapping("/{documento}")
    public ResponseEntity<Void> eliminar(@PathVariable int id_cuenta) {
        cuentaService.eliminar(id_cuenta);
        return ResponseEntity.ok().build();
    }

    // Obtener cuentas por ID de usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Cuenta>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(cuentaService.obtenerPorUsuario(idUsuario));
    }
}

