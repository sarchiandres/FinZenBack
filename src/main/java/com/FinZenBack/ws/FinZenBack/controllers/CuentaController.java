package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.CuentaService;
import com.FinZenBack.ws.FinZenBack.models.DTO.CuentaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Cuenta> createCuenta(@Valid @RequestBody CuentaDto cuentaDto) {
        Cuenta cuenta = cuentaService.createCuenta(cuentaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuenta);
    }

    @GetMapping(value = "/usuario/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or authentication.principal == @usuarioRepository.findById(#idUsuario).map(u -> u.correo).orElse('')")
    public ResponseEntity<List<Cuenta>> getCuentasByUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(cuentaService.getCuentasByUsuario(idUsuario));
    }

    @PutMapping(value = "/{idCuenta}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or @cuentaService.isCuentaOwner(#idCuenta, authentication.principal)")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable Long idCuenta, @Valid @RequestBody CuentaDto cuentaDto) {
        return ResponseEntity.ok(cuentaService.updateCuenta(idCuenta, cuentaDto));
    }

    @DeleteMapping(value = "/{idCuenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or @cuentaService.isCuentaOwner(#idCuenta, authentication.principal)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long idCuenta) {
        cuentaService.deleteCuenta(idCuenta);
        return ResponseEntity.noContent().build();
    }
}