package com.FinZenBack.ws.FinZenBack.models.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GastoDto {
        /*
    *CREATE TABLE GASTO (
    id_gasto BIGINT AUTO_INCREMENT PRIMARY KEY,
    monto DECIMAL(15,2) NOT NULL,
    id_presupuesto BIGINT,
    id_categoria BIGINT,
    fecha DATE NOT NULL,
    descripcion TEXT,
    FOREIGN KEY (id_presupuesto) REFERENCES PRESUPUESTO(id_presupuesto) ON DELETE SET NULL,
    FOREIGN KEY (id_categoria) REFERENCES GASTOCATEGORIA(id_categoria) ON DELETE SET NULL,
    CHECK (monto >= 0)
);
    * */
    private BigDecimal monto;
    private Long idPresupuesto;
    private long idCategoria ;
    private LocalDate fecha;
    private String descripcion;

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Long getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(Long idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
