package com.firma.data.repository;

import com.firma.data.model.Firma;
import com.firma.data.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirmaRepository extends JpaRepository<Firma, Integer> {
    @Query("SELECT f FROM Firma f WHERE f.nombre = :nombre")
    List<Firma> findByName(String nombre);
}
