package com.FinZenBack.ws.FinZenBack.models.DTO;

import com.FinZenBack.ws.FinZenBack.models.Entities.Meta.EstadoMeta;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MetaDto {
    @NotBlank
    @Size(max = 150)
    private String titulo;

    @Size(max = 500)
    private String descripcion;

    @NotNull
    @PastOrPresent
    private LocalDate fechaInicio;

    @NotNull
    @FutureOrPresent
    private LocalDate fechaLimite;

    @NotNull
    private Long idCuenta;

    private Boolean enProgreso = true;

    @NotNull
    private EstadoMeta estado = EstadoMeta.creado;

    @PositiveOrZero
    private BigDecimal valor;

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

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
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