package com.FinZenBack.ws.FinZenBack.models.DTO;

import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda.EstadoDeuda;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DeudaDto {
    @NotNull
    private Long idCuenta;

    @NotNull
    @Positive
    private BigDecimal monto;

    @NotNull
    @PositiveOrZero
    private BigDecimal montoPagado;

    @NotNull
    @FutureOrPresent
    private LocalDate fechaVencimiento;

    @NotNull
    private EstadoDeuda estado = EstadoDeuda.pendiente;

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public EstadoDeuda getEstado() {
        return estado;
    }

    public void setEstado(EstadoDeuda estado) {
        this.estado = estado;
    }
}