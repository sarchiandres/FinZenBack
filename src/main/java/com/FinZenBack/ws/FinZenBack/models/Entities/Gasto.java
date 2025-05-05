package com.FinZenBack.ws.FinZenBack.models.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="GASTO")
public class Gasto {

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_gasto")
    private Long idGasto;

    @Column(name="monto")
    private BigDecimal monto;

    @ManyToOne
    @JoinColumn(name="id_presupuesto",nullable = false)
    @JsonBackReference
    private  Presuspuesto presuspuesto ;

    @ManyToOne
    @JoinColumn(name="id_categoria")
    @JsonBackReference
    private CategoriaGasto categoria;

    @Column(name="fecha")
    private LocalDate fecha;

    @Column(name="descripcion")
    private String descripcion;


    public Long getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(Long idGasto) {
        this.idGasto = idGasto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Presuspuesto getPresuspuesto() {
        return presuspuesto;
    }

    public void setPresuspuesto(Presuspuesto presuspuesto) {
        this.presuspuesto = presuspuesto;
    }

    public CategoriaGasto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaGasto categoria) {
        this.categoria = categoria;
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
