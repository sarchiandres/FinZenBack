package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.SoporteServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.SoporteDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Soporte;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/soporte")
public class SoporteController {

    private final SoporteServices soporteServices;

    public SoporteController(SoporteServices soporteServices) {
        this.soporteServices = soporteServices;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Soporte> crearSoporte(@Valid @RequestBody SoporteDto soporteDto) {
        Soporte soporte = soporteServices.createSoporte(soporteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(soporte);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Soporte>> listarSoportesPorUsuario() {
        return ResponseEntity.ok(soporteServices.getSoportesByUsuario());
    }

    @GetMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Soporte>> listarTodosSoportes() {
        return ResponseEntity.ok(soporteServices.getAllSoportes());
    }
}