package com.firma.data.repository;

import com.firma.data.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u " +
            "JOIN Empleado e ON u.id = e.usuario.id " +
            "JOIN Firma f ON f.id = e.firma.id " +
            "WHERE f.id = :firmaId and u.rol.id = :rolId")
    Set<Usuario> findAllAbogadosByFirma(Integer firmaId, Integer rolId);

    @Query(value = "SELECT COUNT(*) AS veces " +
            "FROM proceso p " +
            "INNER JOIN empleado e ON e.id = p.empleadoid " +
            "INNER JOIN usuario u ON u.id = e.usuarioid " +
            "WHERE u.id = :userId " +
            "GROUP BY u.id", nativeQuery = true)
    int getNumberAssignedProcesses(int userId);
}