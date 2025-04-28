package com.FinZenBack.ws.FinZenBack.models.Entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "DEUDA")
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deuda")
    private Integer idDeuda;

    @Column(name = "id_cuenta", nullable = false)
    private Integer idCuenta;

    @Column(name = "monto", nullable = false)
    private BigDecimal monto;

    @Column(name = "monto_pagado", nullable = false)
    private BigDecimal montoPagado = BigDecimal.ZERO;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoDeuda estado = EstadoDeuda.pendiente;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    public enum EstadoDeuda {
        pendiente, pagada
    }

    // Getters y Setters
    public Integer getIdDeuda() {
        return idDeuda;
    }

    public void setIdDeuda(Integer idDeuda) {
        this.idDeuda = idDeuda;
    }

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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}

