package com.FinZenBack.ws.FinZenBack.models.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DeudaDTO {
    private Integer idCuenta;
    private BigDecimal monto;
    private LocalDate fechaVencimiento;
    private Integer idUsuario;


    public DeudaDTO() {
    }

    public DeudaDTO(Integer idCuenta, BigDecimal monto, LocalDate fechaVencimiento, Integer idUsuario) {
        this.idCuenta = idCuenta;
        this.monto = monto;
        this.fechaVencimiento = fechaVencimiento;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
