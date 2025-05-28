package com.FinZenBack.ws.FinZenBack.models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SoporteDto {
    @NotBlank
    @Size(max = 150)
    private String asunto;

    @NotBlank
    @Size(max = 2000)
    private String mensaje;

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}