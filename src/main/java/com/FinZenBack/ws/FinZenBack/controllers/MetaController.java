package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.MetaServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.MetaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Meta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meta> createMeta(@Valid @RequestBody MetaDto miMeta) {
        Meta meta = metaServices.createMeta(miMeta);
        return ResponseEntity.status(HttpStatus.CREATED).body(meta);
    }

    @GetMapping(value = "/cuenta/{idCuenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Meta>> getMetasByIdCuenta(@PathVariable Long idCuenta) {
        List<Meta> metas = metaServices.getMetasByIdCuenta(idCuenta);
        return ResponseEntity.ok(metas);
    }

    @PutMapping(value = "/{idMeta}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meta> updateMeta(@PathVariable Long idMeta, @Valid @RequestBody MetaDto miMeta) {
        Meta updatedMeta = metaServices.updateMeta(idMeta, miMeta);
        return ResponseEntity.ok(updatedMeta);
    }

    @DeleteMapping(value = "/{idMeta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteMeta(@PathVariable Long idMeta) {
        metaServices.deleteMeta(idMeta);
        return ResponseEntity.noContent().build();
    }
}