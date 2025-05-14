package com.FinZenBack.ws.FinZenBack.models.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta")
    private Long idCuenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "moneda_predeterminada", nullable = false, length = 3)
    private String monedaPredeterminada;

    @PositiveOrZero
    @Column(name = "monto", nullable = false)
    private BigDecimal monto = BigDecimal.ZERO;

    @PositiveOrZero
    @Column(name = "monto_ocupado", nullable = false)
    private BigDecimal montoOcupado = BigDecimal.ZERO;

    @PositiveOrZero
    @Column(name = "monto_libre", nullable = false)
    private BigDecimal montoLibre = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Meta> metas = new ArrayList<>();

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Deuda> deudas = new ArrayList<>();

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Presupuesto> presupuestos = new ArrayList<>();
    // Getters y setters

    public long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getMonedaPredeterminada() {
        return monedaPredeterminada;
    }

    public void setMonedaPredeterminada(String monedaPredeterminada) {
        this.monedaPredeterminada = monedaPredeterminada;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getMontoOcupado() {
        return montoOcupado;
    }

    public void setMontoOcupado(BigDecimal montoOcupado) {
        this.montoOcupado = montoOcupado;
    }

    public BigDecimal getMontoLibre() {
        return montoLibre;
    }

    public void setMontoLibre(BigDecimal montoLibre) {
        this.montoLibre = montoLibre;
    }

    public List<Meta> getMetas() {
        return metas;
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }

    public List<Deuda> getDeudas() {
        return deudas;
    }

    public void setDeudas(List<Deuda> deudas) {
        this.deudas = deudas;
    }
}


