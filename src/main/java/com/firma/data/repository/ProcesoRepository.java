package com.firma.data.repository;

import com.firma.data.model.Proceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface ProcesoRepository extends JpaRepository<Proceso, Integer> {
    @Query("SELECT p FROM Proceso p " +
            "JOIN EstadoProceso ep ON p.estadoproceso.id = ep.id " +
            "WHERE p.firma.id = :firmaId AND ep.nombre = 'Retirado' ")
    Set<Proceso> findAllByFirma(Integer firmaId);

    @Query("SELECT p FROM Proceso p WHERE " +
            "(:fechaInicio IS NULL OR p.fecharadicado >= :fechaInicio) " +
            "AND (:fechaFin IS NULL OR p.fecharadicado <= :fechaFin) " +
            "AND (:estadosProceso IS NULL OR p.estadoproceso.nombre IN :estadosProceso) " +
            "AND (:tipoProceso IS NULL OR p.tipoproceso.nombre = :tipoProceso)")
    Set<Proceso> findByFiltros(LocalDate fechaInicio, LocalDate fechaFin, List<String> estadosProceso, String tipoProceso);
}