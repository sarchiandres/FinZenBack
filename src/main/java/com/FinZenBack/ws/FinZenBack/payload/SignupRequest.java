package com.FinZenBack.ws.FinZenBack.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String nombreUsuario;

    @NotBlank
    @Size(max = 50)
    @Email
    private String correo;

    @NotBlank
    @Size(min = 6, max = 40)
    private String contrasena;

    @NotBlank
    private String nombre;

    @NotBlank
    private Long numeroDocumento;

    @NotBlank
    private String tipoDocumento;

    private String paisResidencia;

    private Long ingresoMensual;

    private Boolean metaActual;

    private String tipoPersona;
}