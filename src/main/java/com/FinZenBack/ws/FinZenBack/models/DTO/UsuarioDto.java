package com.FinZenBack.ws.FinZenBack.models.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioDto {


    private String nombre;
    private String correo;
    private String contrasena;
    private Long numeroDocumento;
    private String tipoDocumento;
    private String paisResidencia;
    private Long ingresoMensual;
    private Boolean metaActual;
    private String nombreUsuario;
    private String tipousuario;

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getPaisResidencia() {
        return paisResidencia;
    }

    public void setPaisResidencia(String paisResidencia) {
        this.paisResidencia = paisResidencia;
    }

    public Long getIngresoMensual() {
        return ingresoMensual;
    }

    public void setIngresoMensual(Long ingresoMensual) {
        this.ingresoMensual = ingresoMensual;
    }

    public Boolean getMetaActual() {
        return metaActual;
    }

    public void setMetaActual(Boolean metaActual) {
        this.metaActual = metaActual;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(String tipousuario) {
        this.tipousuario = tipousuario;
    }
}