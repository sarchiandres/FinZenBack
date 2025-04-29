package com.FinZenBack.ws.FinZenBack.models.DTO;


import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda;
import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda.EstadoDeuda;

import java.math.BigDecimal;
import java.time.LocalDate;

/*CREATE TABLE DEUDA (
    id_deuda BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cuenta BIGINT NOT NULL,
    monto DECIMAL(15,2) NOT NULL,
    monto_pagado DECIMAL(15,2) DEFAULT 0.00,
    fecha_vencimiento DATE NOT NULL,
    estado ENUM('pendiente', 'pagada') DEFAULT 'pendiente',
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cuenta) REFERENCES CUENTA(id_cuenta)
);*/
public class DeudaDto {

    private Long idCuenta;
    private BigDecimal monto;
    private BigDecimal montoPagado;
    private LocalDate fechaVencimiento ;
    private EstadoDeuda estado= EstadoDeuda.pendiente;

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
