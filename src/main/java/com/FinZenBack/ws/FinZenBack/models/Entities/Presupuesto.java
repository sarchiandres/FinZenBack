package com.FinZenBack.ws.FinZenBack.models.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRESUPUESTO")
public class Presupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_presupuesto")
    private Long idPresupuesto;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "monto_asignado")
    private BigDecimal montoAsignado;

    @ManyToOne
    @JoinColumn(name = "id_cuenta", nullable = false)
    @JsonIgnore
    private Cuenta cuenta;

    @ManyToOne
    @JoinColumn(name = "id_categoriapresupuesto", nullable = false)
    @JsonIgnore
    private CategoriaPresupuesto categoria;


    @OneToMany(mappedBy = "presupuesto" ,cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Gasto> gastos = new ArrayList<>();


    @OneToMany(mappedBy = "presupuesto" ,cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Ingreso> ingresos = new ArrayList<>();


    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public Long getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(Long idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public BigDecimal getMontoAsignado() {
        return montoAsignado;
    }

    public void setMontoAsignado(BigDecimal montoAsignado) {
        this.montoAsignado = montoAsignado;
    }

    public CategoriaPresupuesto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaPresupuesto categoria) {
        this.categoria = categoria;
    }
}
