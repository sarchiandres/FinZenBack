package com.FinZenBack.ws.FinZenBack.models.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="META")
public class Meta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMeta;

    @Column(name = "titulo" ,length = 150)
    private String titulo;

    @Column(name= "descripcion")
    private String descripcion;

    @Column(name="fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name="fecha_limite")
    private LocalDate fechaLimite;

    @ManyToOne
    @JoinColumn(name="id_cuenta",nullable = false)
    @JsonBackReference
    private Cuenta cuenta;

    @Column(name="en_progreso")
    private Boolean enProgreso;

    @Enumerated(EnumType.STRING)
    @Column(name="estado")
    private EstadoMeta estado = EstadoMeta.creado;

    @Column(name="valor")
    private BigDecimal valor;


    public enum EstadoMeta {
        creado,
        iniciado,
        terminado
    }



    public long getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(long idMeta) {
        this.idMeta = idMeta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Boolean getEnProgreso() {
        return enProgreso;
    }

    public void setEnProgreso(Boolean enProgreso) {
        this.enProgreso = enProgreso;
    }

    public EstadoMeta getEstado() {
        return estado;
    }

    public void setEstado(EstadoMeta estado) {
        this.estado = estado;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
