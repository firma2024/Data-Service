package com.firma.data.repository;

import com.firma.data.model.EstadoProceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoProcesoRepository extends JpaRepository<EstadoProceso, Integer> {
}