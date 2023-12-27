package com.firma.data.repository;

import com.firma.data.model.Proceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcesoRepository extends JpaRepository<Proceso, Integer> {
    @Query("SELECT p FROM Proceso p WHERE p.firma.id = :firmaId")
    List<Proceso> findAllByFirma(Integer firmaId);
}