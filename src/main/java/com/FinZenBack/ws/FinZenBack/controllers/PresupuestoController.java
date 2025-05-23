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

    // PresupuestoController.java
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PresupuestoDto> crearPresupuesto(@Valid @RequestBody PresupuestoDto presupuestoDto) {
        Presupuesto presupuesto = presupuestoServices.createPresupuesto(presupuestoDto);
        PresupuestoDto responseDto = mapToDto(presupuesto); // Convert to DTO
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PresupuestoDto> actualizarPresupuesto(@PathVariable Long id, @Valid @RequestBody PresupuestoDto presupuestoDto) {
        Presupuesto presupuesto = presupuestoServices.updatePresupuesto(id, presupuestoDto);
        PresupuestoDto responseDto = mapToDto(presupuesto); // Convert to DTO
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping(value = "/cuenta/{idCuenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PresupuestoDto>> listarPresupuestosPorCuenta(@PathVariable Long idCuenta) {
        List<Presupuesto> presupuestos = presupuestoServices.getPresupuestos(idCuenta);
        List<PresupuestoDto> responseDtos = presupuestos.stream()
                .map(this::mapToDto)
                .toList(); // Convert to DTO list
        return ResponseEntity.ok(responseDtos);
    }

    // Helper method to map Presupuesto to PresupuestoDto
    private PresupuestoDto mapToDto(Presupuesto presupuesto) {
        PresupuestoDto dto = new PresupuestoDto();
        dto.setNombre(presupuesto.getNombre());
        dto.setMontoAsignado(presupuesto.getMontoAsignado());
        dto.setIdCuenta(presupuesto.getCuenta().getIdCuenta());
        if (presupuesto.getCategoria() != null) {
            dto.setIdCategoria(presupuesto.getCategoria().getIdCategoria());
        }
        return dto;
    }
}