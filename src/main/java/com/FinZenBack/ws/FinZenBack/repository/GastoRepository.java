package com.FinZenBack.ws.FinZenBack.repository;

import com.FinZenBack.ws.FinZenBack.models.Entities.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto,Long> {
    List<Gasto> findByPresupuestoIdPresupuesto(Long id);
}

