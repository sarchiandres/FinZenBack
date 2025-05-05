package com.FinZenBack.ws.FinZenBack.Services;


import com.FinZenBack.ws.FinZenBack.models.DTO.CategoriaPresupuestoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaPresupuesto;
import com.FinZenBack.ws.FinZenBack.repository.CategoriaPresupuestoRepository;

import java.util.List;

public class CategoriaPresupuestoServices {

    private final CategoriaPresupuestoRepository categoriaPrepository;

    public CategoriaPresupuestoServices(CategoriaPresupuestoRepository categoriaPrepository) {
        this.categoriaPrepository = categoriaPrepository;
    }

    public CategoriaPresupuesto createCategoria (CategoriaPresupuestoDto categoriaDto){
        if(categoriaPrepository.findByNombre(categoriaDto.getNombre()).isPresent()){
            throw new RuntimeException("La categoria con el nombre "+categoriaDto.getNombre()+" ya existe");
        }
        CategoriaPresupuesto categoria = new CategoriaPresupuesto();

        categoria.setNombre(categoriaDto.getNombre());
        return categoriaPrepository.save(categoria);
    }

    public CategoriaPresupuesto updateCategoria (long idCategoria,CategoriaPresupuestoDto categoriaDto){
        CategoriaPresupuesto categoria = categoriaPrepository.findById(idCategoria)
                .orElseThrow(()-> new RuntimeException("la categoria no se encontro"));

        categoria.setNombre(categoriaDto.getNombre());
        return categoriaPrepository.save(categoria);
    }

    public List<CategoriaPresupuesto> getCategorias(){
        return categoriaPrepository.findAll();
    }

    public void DeleteCategoria (long idCategoria){
        if(!categoriaPrepository.existsById(idCategoria)){
            throw new RuntimeException("la categoria no existe");
        }
        categoriaPrepository.deleteById(idCategoria);
    }
}
