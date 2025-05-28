package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.CategoriaPresupuestoServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.CategoriaPresupuestoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaPresupuesto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/categoria-presupuesto")
public class CategoriaPresupuestoController {

    private final CategoriaPresupuestoServices categoriaServices;

    public CategoriaPresupuestoController(CategoriaPresupuestoServices categoriaServices) {
        this.categoriaServices = categoriaServices;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoriaPresupuesto> crearCategoria(@Valid @RequestBody CategoriaPresupuestoDto dto) {
        CategoriaPresupuesto categoria = categoriaServices.createCategoria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @PutMapping(value = "/{idCategoria}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoriaPresupuesto> actualizarCategoria(@PathVariable Long idCategoria, @Valid @RequestBody CategoriaPresupuestoDto dto) {
        CategoriaPresupuesto categoria = categoriaServices.updateCategoria(idCategoria, dto);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoriaPresupuesto>> listarCategorias() {
        return ResponseEntity.ok(categoriaServices.getCategorias());
    }

    @DeleteMapping(value = "/{idCategoria}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long idCategoria) {
        categoriaServices.DeleteCategoria(idCategoria);
        return ResponseEntity.noContent().build();
    }
}