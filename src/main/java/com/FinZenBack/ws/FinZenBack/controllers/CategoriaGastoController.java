package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.CategoriaGastoServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.CategoriaGastoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaGasto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/categoria-gasto")
public class CategoriaGastoController {

    private final CategoriaGastoServices categoriaGastoServices;

    public CategoriaGastoController(CategoriaGastoServices categoriaGastoServices) {
        this.categoriaGastoServices = categoriaGastoServices;
    }

    @PostMapping
    public CategoriaGasto crearCategoria(@RequestBody CategoriaGastoDto dto) {
        return categoriaGastoServices.createCategoria(dto);
    }

    @PutMapping("/{idCategoria}")
    public CategoriaGasto actualizarCategoria(@PathVariable Long idCategoria, @RequestBody CategoriaGastoDto dto) {
        return categoriaGastoServices.updateCategoria(idCategoria, dto);
    }

    @GetMapping
    public List<CategoriaGasto> listarCategorias() {
        return categoriaGastoServices.getCategorias();
    }

    @DeleteMapping("/{idCategoria}")
    public void eliminarCategoria(@PathVariable Long idCategoria) {
        categoriaGastoServices.deleteCategoria(idCategoria);
    }
}
