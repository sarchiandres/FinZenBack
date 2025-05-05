package com.FinZenBack.ws.FinZenBack.models.Entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="CATEGORIAPRESUPUESTO")
public class CategoriaPresupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoriapresupuesto")
    private Long idCategoria;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "categoria" ,cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Presuspuesto> presuspuestos = new ArrayList<>();

    //Getter and Setter


    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Presuspuesto> getPresuspuestos() {
        return presuspuestos;
    }

    public void setPresuspuestos(List<Presuspuesto> presuspuestos) {
        this.presuspuestos = presuspuestos;
    }
}
