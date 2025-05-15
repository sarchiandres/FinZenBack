package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.IngresoServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.IngresoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Ingreso;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/ingresos")
public class IngresoController {
    private final IngresoServices ingresoService;

    public IngresoController(IngresoServices ingresoService) {
        this.ingresoService = ingresoService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ingreso> crearIngreso(@Valid @RequestBody IngresoDto dto) {
        Ingreso ingreso = ingresoService.createIngreso(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingreso);
    }

    @GetMapping(value = "/presupuesto/{idPresupuesto}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ingreso>> listarIngresos(@PathVariable Long idPresupuesto) {
        return ResponseEntity.ok(ingresoService.getIngresosByPresupuesto(idPresupuesto));
    }

    @DeleteMapping(value = "/{idIngreso}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminarIngreso(@PathVariable Long idIngreso) {
        ingresoService.deleteIngreso(idIngreso);
        return ResponseEntity.noContent().build();
    }
}