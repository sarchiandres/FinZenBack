package com.FinZenBack.ws.FinZenBack.models.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "correo", nullable = false, unique = true)
    private String correo;

    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    @Column(name = "numero_documento", nullable = false, unique = true)
    private Long numeroDocumento;

    @Column(name = "tipo_documento", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDocumentoEnum tipoDocumento;

    @Column(name = "pais_residencia")
    private String paisResidencia;

    @Column(name = "ingreso_mensual", nullable = false)
    private Long ingresoMensual = 0L;

    @Column(name = "meta_actual", nullable = false)
    private Boolean metaActual = true;

    @Column(name = "nombre_usuario", unique = true)
    private String nombreUsuario;

    @Column(name = "tipo_persona") // Fixed to match schema
    @Enumerated(EnumType.STRING)
    private TipoPersonaEnum tipoPersona;

    @ManyToOne
    @JoinColumn(name = "id_tipousuario", nullable = false)
    @JsonIgnore
    private TipoUsuario tipoUsuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonManagedReference
    private List<Cuenta> cuentas = new ArrayList<>();

    public enum TipoDocumentoEnum {
        cedula, pasaporte, tarjeta_de_identidad, cedula_extranjera
    }

    public enum TipoPersonaEnum {
        padre_de_familia, joven_profesional, jubilado, personalizado
    }

    // Getters and setters (unchanged)
    public Long getIdUsuario() { return idUsuario; }

    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }

    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public Long getNumeroDocumento() { return numeroDocumento; }

    public void setNumeroDocumento(Long numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public TipoDocumentoEnum getTipoDocumento() { return tipoDocumento; }

    public void setTipoDocumento(TipoDocumentoEnum tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getPaisResidencia() { return paisResidencia; }
    public void setPaisResidencia(String paisResidencia) { this.paisResidencia = paisResidencia; }

    public Long getIngresoMensual() { return ingresoMensual; }

    public void setIngresoMensual(Long ingresoMensual) { this.ingresoMensual = ingresoMensual; }

    public Boolean getMetaActual() { return metaActual; }

    public void setMetaActual(Boolean metaActual) { this.metaActual = metaActual; }

    public String getNombreUsuario() { return nombreUsuario; }

    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public TipoPersonaEnum getTipoPersona() { return tipoPersona; }

    public void setTipoPersona(TipoPersonaEnum tipoPersona) { this.tipoPersona = tipoPersona; }

    public TipoUsuario getTipoUsuario() { return tipoUsuario; }

    public void setTipoUsuario(TipoUsuario tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public List<Cuenta> getCuentas() { return cuentas; }

    public void setCuentas(List<Cuenta> cuentas) { this.cuentas = cuentas; }

}