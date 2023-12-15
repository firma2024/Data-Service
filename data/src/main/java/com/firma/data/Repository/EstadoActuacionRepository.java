package com.firma.data.Repository;

import com.firma.data.Model.EstadoActuacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoActuacionRepository extends JpaRepository<EstadoActuacion, Integer> {
}