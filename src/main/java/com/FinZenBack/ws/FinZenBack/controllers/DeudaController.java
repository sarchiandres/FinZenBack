package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.DeudaServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.DeudaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finzen/deuda")
public class DeudaController {
    private final DeudaServices deudaServices;

    public DeudaController(DeudaServices deudaServices) {
        this.deudaServices = deudaServices;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Deuda> createDeuda(@Valid @RequestBody DeudaDto deudaDto) {
        Deuda deuda = deudaServices.createDeuda(deudaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(deuda);
    }

    @GetMapping(value = "/cuenta/{idCuenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Deuda>> getDeudas(@PathVariable Long idCuenta) {
        List<Deuda> deudas = deudaServices.getDeudas(idCuenta);
        return ResponseEntity.ok(deudas);
    }

    @PutMapping(value = "/{idDeuda}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Deuda> updateDeuda(@PathVariable Long idDeuda, @Valid @RequestBody DeudaDto deudaDto) {
        Deuda deuda = deudaServices.updateDeuda(idDeuda, deudaDto);
        return ResponseEntity.ok(deuda);
    }

    @DeleteMapping(value = "/{idDeuda}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteDeuda(@PathVariable Long idDeuda) {
        deudaServices.deleteDeuda(idDeuda);
        return ResponseEntity.noContent().build();
    }


}