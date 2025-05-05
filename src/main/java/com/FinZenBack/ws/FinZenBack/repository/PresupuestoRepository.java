package com.FinZenBack.ws.FinZenBack.repository;

import com.FinZenBack.ws.FinZenBack.models.Entities.Meta;
import com.FinZenBack.ws.FinZenBack.models.Entities.Presuspuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PresupuestoRepository extends JpaRepository<Presuspuesto,Long> {
    List<Presuspuesto> findByCuentaIdCuenta(long idCuenta);
}
