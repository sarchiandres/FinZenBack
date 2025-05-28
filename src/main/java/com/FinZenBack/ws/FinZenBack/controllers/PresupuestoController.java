package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.PresupuestoServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.PresupuestoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Presupuesto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/finzen/presupuesto")
public class PresupuestoController {

    private final PresupuestoServices presupuestoServices;

    public PresupuestoController(PresupuestoServices presupuestoServices) {
        this.presupuestoServices = presupuestoServices;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Presupuesto> crearPresupuesto(@Valid @RequestBody PresupuestoDto presupuestoDto) {
        Presupuesto presupuesto = presupuestoServices.createPresupuesto(presupuestoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(presupuesto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Presupuesto> actualizarPresupuesto(@PathVariable Long id, @Valid @RequestBody PresupuestoDto presupuestoDto) {
        Presupuesto presupuesto = presupuestoServices.updatePresupuesto(id, presupuestoDto);
        return ResponseEntity.ok(presupuesto);
    }

    @GetMapping(value = "/cuenta/{idCuenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Presupuesto>> listarPresupuestosPorCuenta(@PathVariable Long idCuenta) {
        return ResponseEntity.ok(presupuestoServices.getPresupuestos(idCuenta));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminarPresupuesto(@PathVariable Long id) {
        presupuestoServices.deletePresupuesto(id);
        return ResponseEntity.noContent().build();
    }
}