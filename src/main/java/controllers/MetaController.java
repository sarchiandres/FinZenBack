package controllers;

import models.Meta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.MetaService;

import java.util.List;

@RestController
@RequestMapping("/api/metas")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @GetMapping
    public ResponseEntity<List<models.Meta>> getMetas() {
        return ResponseEntity.ok(metaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<models.Meta> getMetaById(@PathVariable Long id) {
        return metaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Meta> createMeta(@RequestBody Meta meta) {
        return ResponseEntity.ok(metaService.guardar(meta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meta> updateMeta(@PathVariable Long id, @RequestBody Meta metaActualizada) {
        return metaService.obtenerPorId(id)
                .map(meta -> {
                    meta.setTitulo(metaActualizada.getTitulo());
                    meta.setDescripcion(metaActualizada.getDescripcion());
                    meta.setFechaInicio(metaActualizada.getFechaInicio());
                    meta.setFechaLimite(metaActualizada.getFechaLimite());
                    meta.setEnProgreso(metaActualizada.getEnProgreso());
                    meta.setEstado(metaActualizada.getEstado());
                    meta.setValor(metaActualizada.getValor());
                    meta.setUsuario(metaActualizada.getUsuario());
                    return ResponseEntity.ok(metaService.guardar(meta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMeta(@PathVariable Long id) {
        return metaService.obtenerPorId(id).map(meta -> {
            metaService.eliminar(id);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

}

