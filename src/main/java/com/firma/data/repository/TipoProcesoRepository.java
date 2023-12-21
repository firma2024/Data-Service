package com.firma.data.repository;

import com.firma.data.model.TipoProceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProcesoRepository extends JpaRepository<TipoProceso, Integer> {
}