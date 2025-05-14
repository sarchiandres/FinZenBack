package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.DeudaServices;
import com.FinZenBack.ws.FinZenBack.models.DTO.DeudaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda;
import com.FinZenBack.ws.FinZenBack.models.Entities.Meta;
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

    @PostMapping
    public ResponseEntity<Deuda> createDeuda(@RequestBody DeudaDto deudaDto){
        return ResponseEntity.ok(deudaServices.CreateDeuda(deudaDto));
    }

    @GetMapping("/{idCuenta}")
    public ResponseEntity<List<Deuda>> getDeudas (@PathVariable long idCuenta){
        List<Deuda> deudas = deudaServices.getDeudas(idCuenta);
        if (deudas.isEmpty()) {
            return ResponseEntity.status(404).body(null); // En caso de no encontrar metas
        }
        return ResponseEntity.ok(deudas);
    }

    @PutMapping("/{idCuenta}")
    public ResponseEntity<Deuda> updateDeuda(@PathVariable long idCuenta,@RequestBody DeudaDto deudaDto){
        return  ResponseEntity.ok(deudaServices.updateDeuda(idCuenta,deudaDto));
    }

    @DeleteMapping("/{idDeuda}")
    public ResponseEntity<String> deleteDeuda (@PathVariable long idDeuda){
        try {
            deudaServices.deleteDeuda(idDeuda);
            return ResponseEntity.ok("Deuda eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Deuda  no encontrada");
        }
    }
}
