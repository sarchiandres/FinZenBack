package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.DeudaService;
import com.FinZenBack.ws.FinZenBack.models.DTO.DeudaDTO;
import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deudas")
public class DeudaController {

    private final DeudaService deudaService;

    @Autowired
    public DeudaController(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    @PostMapping
    public Deuda crearDeuda(@RequestBody DeudaDTO deudaDTO) {
        return deudaService.crearDeuda(deudaDTO);
    }

    @GetMapping
    public List<Deuda> listarDeudas() {
        return deudaService.listarDeudas();
    }
}
