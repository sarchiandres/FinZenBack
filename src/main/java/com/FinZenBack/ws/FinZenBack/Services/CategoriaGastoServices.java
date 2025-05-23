package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.CategoriaGastoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaGasto;
import com.FinZenBack.ws.FinZenBack.repository.CategoriaGastoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaGastoServices {
    private final CategoriaGastoRepository categoriaGastoRepository;

    public CategoriaGastoServices(CategoriaGastoRepository categoriaGastoRepository) {
        this.categoriaGastoRepository = categoriaGastoRepository;
    }

    @Transactional
    public CategoriaGastoDto createCategoria(CategoriaGastoDto categoriaDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo administradores pueden crear categorías de gasto");
        }

        if (categoriaDto.getNombre() == null || categoriaDto.getNombre().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de la categoría es obligatorio");
        }
        if (categoriaGastoRepository.findByNombre(categoriaDto.getNombre()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La categoría con el nombre " + categoriaDto.getNombre() + " ya existe");
        }

        CategoriaGasto categoria = new CategoriaGasto();
        categoria.setNombre(categoriaDto.getNombre());
        categoria = categoriaGastoRepository.save(categoria);

        return new CategoriaGastoDto(categoria.getIdCategoria(), categoria.getNombre());
    }

    @Transactional
    public CategoriaGastoDto updateCategoria(Long idCategoria, CategoriaGastoDto categoriaDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo administradores pueden actualizar categorías de gasto");
        }

        if (categoriaDto.getNombre() == null || categoriaDto.getNombre().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de la categoría es obligatorio");
        }
        CategoriaGasto categoria = categoriaGastoRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría con ID " + idCategoria + " no se encontró"));

        if (!categoria.getNombre().equals(categoriaDto.getNombre()) &&
                categoriaGastoRepository.findByNombre(categoriaDto.getNombre()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La categoría con el nombre " + categoriaDto.getNombre() + " ya existe");
        }

        categoria.setNombre(categoriaDto.getNombre());
        categoria = categoriaGastoRepository.save(categoria);

        return new CategoriaGastoDto(categoria.getIdCategoria(), categoria.getNombre());
    }

    @Transactional(readOnly = true)
    public List<CategoriaGastoDto> getCategorias() {
        return categoriaGastoRepository.findAll().stream()
                .map(categoria -> new CategoriaGastoDto(categoria.getIdCategoria(), categoria.getNombre()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCategoria(Long idCategoria) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo administradores pueden eliminar categorías de gasto");
        }

        if (!categoriaGastoRepository.existsById(idCategoria)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría con ID " + idCategoria + " no existe");
        }
        categoriaGastoRepository.deleteById(idCategoria);
    }
}