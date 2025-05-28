package com.FinZenBack.ws.FinZenBack.payload;

public class SignupRequest {
    private String nombre;
    private String correo;
    private String contrasena;
    private Long numeroDocumento;
    private String tipoDocumento;
    private String paisResidencia;
    private Long ingresoMensual;
    private Boolean metaActual;
    private String nombreUsuario;
    private String tipoPersona;
    private String role; // Para definir si es USUARIO o ADMIN

    // Getters and Setters
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

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "SignupRequest{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", numeroDocumento=" + numeroDocumento +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", paisResidencia='" + paisResidencia + '\'' +
                ", ingresoMensual=" + ingresoMensual +
                ", metaActual=" + metaActual +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", tipoPersona='" + tipoPersona + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}