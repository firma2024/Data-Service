package com.firma.data.repository;

import com.firma.data.model.TipoAbogado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAbogadoRepository extends JpaRepository<TipoAbogado, Integer> {
}