package com.FinZenBack.ws.FinZenBack.models.DTO;
import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda;
import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda.EstadoDeuda;

import java.math.BigDecimal;
import java.time.LocalDate;

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
