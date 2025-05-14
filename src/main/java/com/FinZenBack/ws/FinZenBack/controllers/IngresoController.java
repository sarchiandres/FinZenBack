package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.IngresoServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.IngresoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Ingreso;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/ingresos")
public class IngresoController {

    private final IngresoServices ingresoService;

    public IngresoController(IngresoServices ingresoService) {
        this.ingresoService = ingresoService;
    }

    @PostMapping
    public Ingreso crearIngreso(@RequestBody IngresoDto dto) {
        return ingresoService.createIngreso(dto);
    }

    @GetMapping("/presupuesto/{idPresupuesto}")
    public List<Ingreso> listarIngresos(@PathVariable Long idPresupuesto) {
        return ingresoService.getIngresosByPresupuesto(idPresupuesto);
    }

    @DeleteMapping("/{idIngreso}")
    public void eliminarIngreso(@PathVariable Long idIngreso) {
        ingresoService.deleteIngreso(idIngreso);
    }
}
