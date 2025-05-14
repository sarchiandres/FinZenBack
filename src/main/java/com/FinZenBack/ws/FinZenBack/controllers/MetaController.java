package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.MetaServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.MetaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Meta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/metas")
public class MetaController {

    private final MetaServices metaServices;

    public MetaController(MetaServices metaServices) {
        this.metaServices = metaServices;
    }

    // Endpoint para crear una nueva meta
    @PostMapping
    public ResponseEntity<Meta> createMeta( @RequestBody MetaDto miMeta) {
        try {
            Meta meta = metaServices.createMeta( miMeta);
            return ResponseEntity.ok(meta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(null); // En caso de error (usuario no encontrado u otros)
        }
    }

    // Endpoint para obtener las metas por el número de documento del usuario
    @GetMapping("{id}")
    public ResponseEntity<List<Meta>> getMetasByNumeroDocumento(@PathVariable long id) {
        List<Meta> metas = metaServices.getMetasByIdCuenta(id);
        if (metas.isEmpty()) {
            return ResponseEntity.status(404).body(null); // En caso de no encontrar metas
        }
        return ResponseEntity.ok(metas);
    }

    // Endpoint para actualizar una meta existente
    @PutMapping("/{idMeta}")
    public ResponseEntity<Meta> updateMeta(@PathVariable long idMeta, @RequestBody MetaDto miMeta) {
        try {
            Meta updatedMeta = metaServices.updateMeta(idMeta, miMeta);
            return ResponseEntity.ok(updatedMeta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null); // En caso de no encontrar la meta
        }
    }

    // Endpoint para eliminar una meta por su ID
    @DeleteMapping("/{idMeta}")
    public ResponseEntity<String> deleteMeta(@PathVariable long idMeta) {
        try {
            metaServices.deleteMeta(idMeta);
            return ResponseEntity.ok("Meta eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Meta no encontrada");
        }
    }
}
