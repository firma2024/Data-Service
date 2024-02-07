package com.firma.data.repository;

import com.firma.data.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u " +
            "JOIN Empleado e ON u.id = e.usuario.id " +
            "JOIN Firma f ON f.id = e.firma.id " +
            "WHERE f.id = :firmaId and u.rol.id = :rolId")
    Page<Usuario> findAllAbogadosByFirma(Integer firmaId, Integer rolId, Pageable pageable);

    @Query(value = "SELECT COUNT(*) AS veces " +
            "FROM proceso p " +
            "INNER JOIN empleado e ON e.id = p.empleadoid " +
            "INNER JOIN usuario u ON u.id = e.usuarioid " +
            "WHERE u.id = :userId " +
            "GROUP BY u.id", nativeQuery = true)
    Integer getNumberAssignedProcesses(int userId);


    @Query("SELECT u FROM Usuario u " +
            "JOIN EspecialidadAbogado es ON es.usuario.id = u.id " +
            "JOIN TipoAbogado tp ON es.tipoAbogado.id = tp.id " +
            "WHERE(:especialidades IS NULL OR tp.nombre IN :especialidades) ")
    Page<Usuario> findAbogadosByFilter(List<String> especialidades, Pageable paging);

    @Query("SELECT u FROM Usuario u " +
            "JOIN Empleado e ON u.id = e.usuario.id " +
            "JOIN Firma f ON f.id = e.firma.id " +
            "WHERE f.id = :firmaId AND u.rol.nombre = 'ABOGADO' " +
            "ORDER BY u.nombres ASC")
    List<Usuario> findAllNamesAbogadosByFirma(Integer firmaId);

    Usuario findByUsername(String username);
}