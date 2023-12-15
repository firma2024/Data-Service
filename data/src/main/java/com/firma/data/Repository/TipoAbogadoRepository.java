package com.firma.data.Repository;

import com.firma.data.Model.TipoAbogado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAbogadoRepository extends JpaRepository<TipoAbogado, Integer> {
}