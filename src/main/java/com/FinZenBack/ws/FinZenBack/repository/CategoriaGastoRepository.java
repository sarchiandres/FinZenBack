package com.FinZenBack.ws.FinZenBack.repository;

import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaGasto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaGastoRepository extends JpaRepository<CategoriaGasto, Long> {
    Optional<CategoriaGasto> findByNombre(String nombre);
}