package com.FinZenBack.ws.FinZenBack.models.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="GASTOCATEGORIA")
public class CategoriaGasto {
    /*-- Tabla: GASTOCATEGORIA
CREATE TABLE GASTOCATEGORIA (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_categoria")
    private long idCategoria;

    @Column(name="nombre")
    private String nombre;

    @OneToMany(mappedBy = "categoria" ,cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Gasto> gastos = new ArrayList<>();


    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
