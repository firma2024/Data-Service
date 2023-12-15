package com.firma.data.Repository;

import com.firma.data.Model.TipoProceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProcesoRepository extends JpaRepository<TipoProceso, Integer> {
}