package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.InformeService;
import com.FinZenBack.ws.FinZenBack.models.DTO.InformeDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Informe;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/informes")
public class InformeController {
    private final InformeService informeService;

    public InformeController(InformeService informeService) {
        this.informeService = informeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Informe> crearInforme(@Valid @RequestBody InformeDto dto) {
        Informe informe = informeService.createInforme(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(informe);
    }

    @PostMapping(value = "/general/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Informe> generarInformeGeneral(@PathVariable Long idUsuario) {
        Informe informe = informeService.generarInformeGeneral(idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(informe);
    }

    @GetMapping(value = "/usuario/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Informe>> getInformesPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(informeService.getInformesByUsuario(idUsuario));
    }

    @PutMapping(value = "/{idInforme}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Informe> actualizarInforme(@PathVariable Long idInforme, @Valid @RequestBody InformeDto dto) {
        Informe informe = informeService.updateInforme(idInforme, dto);
        return ResponseEntity.ok(informe);
    }

    @DeleteMapping(value = "/{idInforme}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminarInforme(@PathVariable Long idInforme) {
        informeService.deleteInforme(idInforme);
        return ResponseEntity.noContent().build();
    }

}