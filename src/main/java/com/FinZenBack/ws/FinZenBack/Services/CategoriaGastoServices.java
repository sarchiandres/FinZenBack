package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.CategoriaGastoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaGasto;
import com.FinZenBack.ws.FinZenBack.repository.CategoriaGastoRepository;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaGastoServices {
    private final CategoriaGastoRepository categoriaGastoRepository;
    private final UsuarioRepository usuarioRepository;


    public CategoriaGastoServices(CategoriaGastoRepository categoriaGastoRepository, UsuarioRepository usuarioRepository) {
        this.categoriaGastoRepository = categoriaGastoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public CategoriaGasto createCategoria(CategoriaGastoDto categoriaDto){
        CategoriaGasto categoria = new CategoriaGasto();

        categoria.setNombre(categoriaDto.getNombre());

        return categoriaGastoRepository.save(categoria);
    }


    public CategoriaGasto updateCategoria (Long idCategoria, CategoriaGastoDto ctaegoriaDto){
        CategoriaGasto categoria = categoriaGastoRepository.findById(idCategoria)
                .orElseThrow(()->new RuntimeException("la categoria de gasto no se encontro"));

        categoria.setNombre(ctaegoriaDto.getNombre());

        return categoriaGastoRepository.save(categoria);
    }

    public List<CategoriaGasto> getCategorias(){
        return categoriaGastoRepository.findAll();
    }
    public void deleteCategoria(long idCategoria){
        if (!categoriaGastoRepository.existsById(idCategoria)) {
            throw new RuntimeException("Meta no encontrada");
        }

        // Eliminar
        categoriaGastoRepository.deleteById(idCategoria);
    }
}
