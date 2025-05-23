package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.CategoriaGastoServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.CategoriaGastoDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/categoria-gasto")
public class CategoriaGastoController {

    private final CategoriaGastoServices categoriaGastoServices;

    public CategoriaGastoController(CategoriaGastoServices categoriaGastoServices) {
        this.categoriaGastoServices = categoriaGastoServices;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoriaGastoDto> crearCategoria(@Valid @RequestBody CategoriaGastoDto dto) {
        CategoriaGastoDto categoria = categoriaGastoServices.createCategoria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @PutMapping(value = "/{idCategoria}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoriaGastoDto> actualizarCategoria(
            @PathVariable Long idCategoria,
            @Valid @RequestBody CategoriaGastoDto dto) {
        CategoriaGastoDto categoria = categoriaGastoServices.updateCategoria(idCategoria, dto);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoriaGastoDto>> listarCategorias() {
        List<CategoriaGastoDto> categorias = categoriaGastoServices.getCategorias();
        return ResponseEntity.ok(categorias);
    }

    @DeleteMapping(value = "/{idCategoria}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long idCategoria) {
        categoriaGastoServices.deleteCategoria(idCategoria);
        return ResponseEntity.noContent().build();
    }
}