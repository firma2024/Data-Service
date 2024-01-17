package com.firma.data.repository;

import com.firma.data.model.Despacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespachoRepository extends JpaRepository<Despacho, Integer> {
    Despacho findDespachoByNombre(String nombre);

    @Query("SELECT d FROM Despacho d " +
            "JOIN Proceso p ON p.despacho.id = d.id " +
            "WHERE p.id = :procesoId")
    Despacho findDespachoByProceso(Integer procesoId);

    @Query("SELECT d FROM Despacho d WHERE NOT EXISTS ("
            + "SELECT 1 FROM Enlace e WHERE e.despacho.id = d.id AND YEAR(e.fechaconsulta) = :year)")
    List<Despacho> findAllDespachosWithOutLinkByYear(String year);
}