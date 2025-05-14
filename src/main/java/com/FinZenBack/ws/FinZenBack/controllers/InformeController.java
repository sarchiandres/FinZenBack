package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.InformeService;
import com.FinZenBack.ws.FinZenBack.models.DTO.InformeDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Informe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/informes")
public class InformeController {

    @Autowired
    private InformeService informeService;

    @PostMapping
    public Informe crearInforme(@RequestBody InformeDto dto) {
        return informeService.createInforme(dto);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Informe> getInformesPorUsuario(@PathVariable Long idUsuario) {
        return informeService.getInformesByUsuario(idUsuario);
    }

    @PutMapping("/{idInforme}")
    public Informe actualizarInforme(@PathVariable Long idInforme, @RequestBody InformeDto dto) {
        return informeService.updateInforme(idInforme, dto);
    }

    @DeleteMapping("/{idInforme}")
    public void eliminarInforme(@PathVariable Long idInforme) {
        informeService.deleteInforme(idInforme);
    }
}
