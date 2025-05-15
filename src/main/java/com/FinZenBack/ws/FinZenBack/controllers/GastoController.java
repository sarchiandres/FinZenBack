package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.GastoServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.GastoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Gasto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/gasto")
public class GastoController {

    private final GastoServices gastoServices;

    public GastoController(GastoServices gastoServices) {
        this.gastoServices = gastoServices;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Gasto> crearGasto(@Valid @RequestBody GastoDto gastoDto) {
        Gasto gasto = gastoServices.createGasto(gastoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(gasto);
    }

    @GetMapping(value = "/presupuesto/{idPresupuesto}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Gasto>> listarGastosPorPresupuesto(@PathVariable Long idPresupuesto) {
        return ResponseEntity.ok(gastoServices.getGasto(idPresupuesto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Gasto> actualizarGasto(@PathVariable Long id, @Valid @RequestBody GastoDto gastoDto) {
        Gasto gasto = gastoServices.updateGasto(id, gastoDto);
        return ResponseEntity.ok(gasto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminarGasto(@PathVariable Long id) {
        gastoServices.deleteGasto(id);
        return ResponseEntity.noContent().build();
    }
}