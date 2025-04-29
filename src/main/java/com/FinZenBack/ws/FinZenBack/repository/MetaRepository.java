package com.FinZenBack.ws.FinZenBack.repository;

import com.FinZenBack.ws.FinZenBack.models.Entities.Meta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetaRepository extends JpaRepository<Meta, Long> {
    List<Meta> findByCuentaIdCuenta(long idCuenta);
}