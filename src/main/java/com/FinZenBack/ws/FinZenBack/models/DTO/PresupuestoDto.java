package com.FinZenBack.ws.FinZenBack.models.DTO;

import java.math.BigDecimal;
import java.util.concurrent.atomic.LongAccumulator;

public class PresupuestoDto {
    private String nombre;
    private BigDecimal montoAsignado;
    private Long idCuenta;
    private Long idCategory;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getMontoAsignado() {
        return montoAsignado;
    }

    public void setMontoAsignado(BigDecimal montoAsignado) {
        this.montoAsignado = montoAsignado;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }
}