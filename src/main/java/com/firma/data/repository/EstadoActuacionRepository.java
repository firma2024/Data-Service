package com.firma.data.repository;

import com.firma.data.model.EstadoActuacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoActuacionRepository extends JpaRepository<EstadoActuacion, Integer> {
}