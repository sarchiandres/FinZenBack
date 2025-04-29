package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.CuentaService;
import com.FinZenBack.ws.FinZenBack.models.DTO.CuentaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCuenta( @RequestBody CuentaDto cuentaDto) {
        cuentaService.createCuenta( cuentaDto);
    }

    @GetMapping("/{idUsuario}")
    public List<Cuenta> getCuentasByUsuario(@PathVariable Long idUsuario) {
        return cuentaService.getCuentasByUsuario(idUsuario);
    }

    @PutMapping("/{idCuenta}")
    public Cuenta updateCuenta(@PathVariable Long idCuenta, @RequestBody CuentaDto cuentaDto) {
        return cuentaService.updateCuenta(idCuenta, cuentaDto);
    }

    @DeleteMapping("/{idCuenta}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCuenta(@PathVariable Long idCuenta) {
        cuentaService.deleteCuenta(idCuenta);
    }
}
