    package com.FinZenBack.ws.FinZenBack.models.Entities;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @Table(name = "CUENTA")
    public class Cuenta {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_cuenta")
        private long idCuenta;

        @ManyToOne
        @JoinColumn(name = "id_usuario", nullable = false)
        @JsonIgnore
        private Usuario usuario;

        @Column(name = "nombre", length = 100, nullable = false)
        private String nombre;

        @Column(name = "fecha_creacion", nullable = false)
        private LocalDateTime fechaCreacion = LocalDateTime.now();

        @Column(name = "moneda_predeterminada", length = 3, nullable = false)
        private String monedaPredeterminada;

        @Column(name="monto")
        private BigDecimal monto= BigDecimal.ZERO;

        @Column (name="monto_ocupado")
        private BigDecimal montoOcupado ;

        @Column(name="monto_libre")
        private BigDecimal montoLibre = BigDecimal.ZERO;


        @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true,
                fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<Meta> metas = new ArrayList<>();


        @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true,
                fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<Deuda> deudas = new ArrayList<>();

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
