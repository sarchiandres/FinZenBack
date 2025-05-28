package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.CategoriaPresupuestoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaPresupuesto;
import com.FinZenBack.ws.FinZenBack.repository.CategoriaPresupuestoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoriaPresupuestoServices {

    private final CategoriaPresupuestoRepository categoriaRepository;

    public CategoriaPresupuestoServices(CategoriaPresupuestoRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public CategoriaPresupuesto createCategoria(CategoriaPresupuestoDto categoriaDto) {
        if (categoriaDto.getNombre() == null || categoriaDto.getNombre().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de la categoría es obligatorio");
        }
        if (categoriaRepository.findByNombre(categoriaDto.getNombre()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La categoría con el nombre " + categoriaDto.getNombre() + " ya existe");
        }

        CategoriaPresupuesto categoria = new CategoriaPresupuesto();
        categoria.setNombre(categoriaDto.getNombre());
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public CategoriaPresupuesto updateCategoria(Long idCategoria, CategoriaPresupuestoDto categoriaDto) {
        if (categoriaDto.getNombre() == null || categoriaDto.getNombre().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de la categoría es obligatorio");
        }
        CategoriaPresupuesto categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría con ID " + idCategoria + " no se encontró"));

        if (!categoria.getNombre().equals(categoriaDto.getNombre()) &&
                categoriaRepository.findByNombre(categoriaDto.getNombre()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La categoría con el nombre " + categoriaDto.getNombre() + " ya existe");
        }

        categoria.setNombre(categoriaDto.getNombre());
        return categoriaRepository.save(categoria);
    }

    @Transactional(readOnly = true)
    public List<CategoriaPresupuesto> getCategorias() {
        return categoriaRepository.findAll();
    }

    @Transactional
    public void DeleteCategoria(Long idCategoria) {
        if (!categoriaRepository.existsById(idCategoria)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría con ID " + idCategoria + " no existe");
        }
        categoriaRepository.deleteById(idCategoria);
    }
}