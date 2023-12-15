package com.firma.data.Repository;

import com.firma.data.Model.Abogado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbogadoRepository extends JpaRepository<Abogado, Integer> {
}