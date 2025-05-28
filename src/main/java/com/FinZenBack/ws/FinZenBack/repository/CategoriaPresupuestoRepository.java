package com.FinZenBack.ws.FinZenBack.repository;

import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaPresupuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaPresupuestoRepository extends JpaRepository<CategoriaPresupuesto, Long> {
    Optional<CategoriaPresupuesto> findByNombre(String nombre);
}