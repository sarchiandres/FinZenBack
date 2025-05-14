package com.FinZenBack.ws.FinZenBack.models.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="INGRESO")
public class Ingreso {
    /* id_ingreso BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_presupuesto BIGINT NOT NULL,
    monto DECIMAL(15,2) NOT NULL,
    fecha DATE NOT NULL,
    fuente VARCHAR(100),
    FOREIGN KEY (id_presupuesto) REFERENCES PRESUPUESTO(id_presupuesto) ON DELETE CASCADE,
    CHECK (monto >= 0)
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idIngreso;

    @ManyToOne
    @JoinColumn(name="id_presupuesto",nullable = false)
    @JsonBackReference
    private Presupuesto presupuesto;

    @Column(name="nombre")
    private String nombre;

    @Column(name="monto")
    private BigDecimal monto ;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name="fuente")
    private String fuente;

    public long getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(long idIngreso) {
        this.idIngreso = idIngreso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
}
