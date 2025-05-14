package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.CategoriaPresupuestoServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.CategoriaPresupuestoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaPresupuesto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/categoria-presupuesto")
public class CategoriaPresupuestoController {

    private final CategoriaPresupuestoServices categoriaServices;

    public CategoriaPresupuestoController(CategoriaPresupuestoServices categoriaServices) {
        this.categoriaServices = categoriaServices;
    }

    @PostMapping
    public CategoriaPresupuesto crearCategoria(@RequestBody CategoriaPresupuestoDto dto) {
        return categoriaServices.createCategoria(dto);
    }

    @PutMapping("/{idCategoria}")
    public CategoriaPresupuesto actualizarCategoria(@PathVariable Long idCategoria, @RequestBody CategoriaPresupuestoDto dto) {
        return categoriaServices.updateCategoria(idCategoria, dto);
    }

    @GetMapping
    public List<CategoriaPresupuesto> listarCategorias() {
        return categoriaServices.getCategorias();
    }

    @DeleteMapping("/{idCategoria}")
    public void eliminarCategoria(@PathVariable Long idCategoria) {
        categoriaServices.DeleteCategoria(idCategoria);
    }
}
