package com.FinZenBack.ws.FinZenBack.models.Entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta")
    private Long idCuenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "moneda_predeterminada")
    private String monedaPredeterminada;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "monto_ocupado")
    private BigDecimal montoOcupado;

    @Column(name = "monto_libre")
    private BigDecimal montoLibre;

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
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
}