package com.FinZenBack.ws.FinZenBack.controllers;

import com.FinZenBack.ws.FinZenBack.Services.DeudaService;
import com.FinZenBack.ws.FinZenBack.models.DTO.DeudaDTO;
import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deudas")
@Tag(name = "Deudas", description = "Operaciones relacionadas con la gestión de deudas")
public class DeudaController {

    private final DeudaService deudaService;

    @Autowired
    public DeudaController(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    @Operation(summary = "Crear una nueva deuda", description = "Crea una deuda asociada a un usuario y una cuenta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deuda creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Deuda.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    @PostMapping
    public Deuda crearDeuda(@RequestBody DeudaDTO deudaDTO) {
        return deudaService.crearDeuda(deudaDTO);
    }

    @Operation(summary = "Listar todas las deudas", description = "Devuelve una lista de todas las deudas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de deudas obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Deuda.class)))
    })
    @GetMapping
    public List<Deuda> listarDeudas() {
        return deudaService.listarDeudas();
    }
}
