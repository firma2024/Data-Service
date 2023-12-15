package com.firma.data.Repository;

import com.firma.data.Model.EstadoProceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoProcesoRepository extends JpaRepository<EstadoProceso, Integer> {
}