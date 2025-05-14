package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.GastoServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.GastoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Gasto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/gasto")
public class GastoController {

    private final GastoServices gastoServices;

    public GastoController(GastoServices gastoServices) {
        this.gastoServices = gastoServices;
    }

    @PostMapping
    public Gasto crearGasto(@RequestBody GastoDto gastoDto) {
        return gastoServices.createGasto(gastoDto);
    }

    @GetMapping("/presupuesto/{idPresupuesto}")
    public List<Gasto> listarGastosPorPresupuesto(@PathVariable Long idPresupuesto) {
        return gastoServices.getGasto(idPresupuesto);
    }

    @PutMapping("/{id}")
    public Gasto actualizarGasto(@PathVariable Long id, @RequestBody GastoDto gastoDto) {
        return gastoServices.updateGasto(id, gastoDto);
    }

    @DeleteMapping("/{id}")
    public void eliminarGasto(@PathVariable Long id) {
        gastoServices.deleteGasto(id);
    }
}
