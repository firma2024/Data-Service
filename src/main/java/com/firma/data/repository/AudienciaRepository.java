package com.firma.data.repository;

import com.firma.data.model.Audiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AudienciaRepository extends JpaRepository<Audiencia, Integer> {
    @Query("SELECT a FROM Audiencia a WHERE a.proceso.id = :procesoId")
    Set<Audiencia> findAllByProceso(Integer procesoId);
}