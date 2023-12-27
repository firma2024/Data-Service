package com.firma.data.repository;

import com.firma.data.model.Actuacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActuacionRepository extends JpaRepository<Actuacion, Integer> {
    @Query("SELECT a FROM Actuacion a " +
            "JOIN Proceso p ON a.proceso.id = p.id " +
            "JOIN EstadoActuacion ea ON a.estadoactuacion.id = ea.id " +
            "WHERE p.firma.id = :firmaId AND ea.nombre = 'No visto' ")
    List<Actuacion> findByNoVisto(Integer firmaId);

    @Query("SELECT a FROM Actuacion a " +
            "JOIN Proceso p ON a.proceso.id = p.id " +
            "WHERE p.id = :procesoId")
    List<Actuacion> findAllByProceso(Integer procesoId);
}