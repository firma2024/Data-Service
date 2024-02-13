package com.firma.data.repository;

import com.firma.data.model.Actuacion;
import com.firma.data.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    @Query("SELECT e FROM Empleado e WHERE e.usuario.id = :usuarioId")
    Empleado findEmpleadoByUsuario(Integer usuarioId);
}