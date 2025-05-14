package com.FinZenBack.ws.FinZenBack.models.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="DEUDA")

public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDeuda;

    @ManyToOne
    @JoinColumn(name="id_cuenta",nullable = false)
    @JsonBackReference
    private Cuenta cuenta;


    @Column (name="monto")
    private BigDecimal monto;

    @Column(name="monto_pagado")
    private BigDecimal montoPagado;

    @Column(name="fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name="estado")
    private EstadoDeuda estado;

     @Column(name="fecha_creacion")
     private LocalDateTime fechaCreacion;


    public enum EstadoDeuda {
        pendiente,
        pagada
    }

    public long getIdDeuda() {
        return idDeuda;
    }

    public void setIdDeuda(long idDeuda) {
        this.idDeuda = idDeuda;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
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
}
