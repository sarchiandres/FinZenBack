package com.FinZenBack.ws.FinZenBack.models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CuentaDto {

    @NotBlank
    @Size(min = 3, max = 100)
    private String nombre;

    @NotBlank
    @Size(min = 3, max = 3)
    private String monedaPredeterminada;

    @NotNull
    @PositiveOrZero
    private BigDecimal monto;

    @NotNull
    private Long idUsuario;

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMonedaPredeterminada() {
        return monedaPredeterminada;
    }

    public void setMonedaPredeterminada(String monedaPredeterminada) {
        this.monedaPredeterminada = monedaPredeterminada;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}