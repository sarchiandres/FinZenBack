package com.FinZenBack.ws.FinZenBack.models.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private LocalDate fechaVencimineto;

    @Column(name="estado")
    private EstadoDeuda estado;

     @Column(name="fecha_creacion")
     private LocalDate fechaCreacion;


    public enum EstadoDeuda {
        pendiente,
        pagadda
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

    public LocalDate getFechaVencimineto() {
        return fechaVencimineto;
    }

    public void setFechaVencimineto(LocalDate fechaVencimineto) {
        this.fechaVencimineto = fechaVencimineto;
    }

    public EstadoDeuda getEstado() {
        return estado;
    }

    public void setEstado(EstadoDeuda estado) {
        this.estado = estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
