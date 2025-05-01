    package com.FinZenBack.ws.FinZenBack.models.DTO;

    import java.math.BigDecimal;

    public class CuentaDto {


        private String nombre;
        private String monedaPredeterminada;
        private Long idUsuario;
        private BigDecimal monto;

        // Getters y setters


        public BigDecimal getMonto() {
            return monto;
        }

        public void setMonto(BigDecimal monto) {
            this.monto = monto;
        }

        public Long getIdUsuario() {
            return idUsuario;
        }

        public void setIdUsuario(Long idUsuario) {
            this.idUsuario = idUsuario;
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
    }
