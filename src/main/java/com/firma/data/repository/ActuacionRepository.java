package com.firma.data.repository;

import com.firma.data.model.Actuacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface ActuacionRepository extends JpaRepository<Actuacion, Integer> {
    @Query("SELECT a FROM Actuacion a " +
            "WHERE a.proceso.id = :procesoId AND a.estadoactuacion.nombre = 'No visto' ")
    List<Actuacion> findByNoVisto(Integer procesoId);

    @Query("SELECT a FROM Actuacion a " +
            "JOIN Proceso p ON a.proceso.id = p.id " +
            "WHERE p.id = :procesoId")
    Set<Actuacion> findAllByProceso(Integer procesoId);

    @Query("SELECT a FROM Actuacion a " +
            "WHERE a.proceso.id = :procesoId " +
            "AND (:fechaInicio IS NULL OR a.fechaactuacion >= :fechaInicio) " +
            "AND (:fechaFin IS NULL OR a.fechaactuacion <= :fechaFin) " +
            "AND (:estadoActuacion IS NULL OR a.estadoactuacion.nombre = :estadoActuacion) " )
    Set<Actuacion> findByFiltros(Integer procesoId, LocalDate fechaInicio, LocalDate fechaFin, String estadoActuacion);
}