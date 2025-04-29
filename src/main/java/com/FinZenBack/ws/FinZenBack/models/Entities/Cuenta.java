    package com.FinZenBack.ws.FinZenBack.models.Entities;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
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
        private LocalDateTime fechaCreacion;

        @Column(name = "moneda_predeterminada", length = 3, nullable = false)
        private String monedaPredeterminada;


        @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true,
                fetch = FetchType.EAGER)
        @JsonManagedReference
        private List<Meta> metas = new ArrayList<>();


        @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true,
                fetch = FetchType.EAGER)
        @JsonManagedReference
        private List<Deuda> deuda = new ArrayList<>();

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
    }
