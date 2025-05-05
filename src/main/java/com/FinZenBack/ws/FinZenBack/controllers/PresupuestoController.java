package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.PresupuestoServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.PresupuestoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Presupuesto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/presupuesto")
public class PresupuestoController {

    private final PresupuestoServices presupuestoServices;

    public PresupuestoController(PresupuestoServices presupuestoServices) {
        this.presupuestoServices = presupuestoServices;
    }

    @PostMapping
    public Presupuesto crearPresupuesto(@RequestBody PresupuestoDto presupuestoDto) {
        return presupuestoServices.createPresupuesto(presupuestoDto);
    }

    @PutMapping("/{id}")
    public Presupuesto actualizarPresupuesto(@PathVariable Long id, @RequestBody PresupuestoDto presupuestoDto) {
        return presupuestoServices.updatePresupuesto(id, presupuestoDto);
    }

    @GetMapping("/cuenta/{idCuenta}")
    public List<Presupuesto> listarPresupuestosPorCuenta(@PathVariable Long idCuenta) {
        return presupuestoServices.getPresupuestos(idCuenta);
    }

    @DeleteMapping("/{id}")
    public void eliminarPresupuesto(@PathVariable Long id) {
        presupuestoServices.deletePresupuesto(id);
    }
}
