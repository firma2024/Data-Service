package com.firma.data.repository;

import com.firma.data.model.Enlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnlaceRepository extends JpaRepository<Enlace, Integer> {

    @Query("SELECT e FROM Enlace e " +
            "WHERE e.despacho.id = :despachoId AND YEAR(e.fechaconsulta) = :year")
    Enlace findByDespachoAndYear(Integer despachoId, String year);
}
