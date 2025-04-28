package com.FinZenBack.ws.FinZenBack.repository;


import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeudaRepository extends JpaRepository<Deuda, Integer> {
}
