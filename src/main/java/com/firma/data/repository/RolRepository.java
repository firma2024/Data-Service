package com.firma.data.repository;

import com.firma.data.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombre(String nombre);

    @Query("SELECT r FROM Rol r " +
            "JOIN Usuario u ON r.id = u.rol.id " +
            "WHERE u.username = :username")
    Rol findByUsuario(String username);
}