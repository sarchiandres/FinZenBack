package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.models.DTO.SoporteDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Soporte;
import com.FinZenBack.ws.FinZenBack.Services.SoporteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/soportes")
public class SoporteController {

    private final SoporteService soporteService;

    public SoporteController(SoporteService soporteService) {
        this.soporteService = soporteService;
    }

    @PostMapping
    public Soporte crearSoporte(@RequestBody SoporteDto dto) {
        return soporteService.createSoporte(dto);
    }

    @GetMapping
    public List<Soporte> listarSoportes() {
        return soporteService.getSoportes();
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Soporte> listarSoportesPorUsuario(@PathVariable Long idUsuario) {
        return soporteService.getSoportesByUsuario(idUsuario);
    }
}
