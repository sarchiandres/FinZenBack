package com.FinZenBack.ws.FinZenBack.repository;

import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda;
import com.FinZenBack.ws.FinZenBack.models.Entities.Meta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeudaRepository extends JpaRepository<Deuda,Long> {
    List<Deuda> findByCuentaIdCuenta(long idCuenta);
}
