package com.firma.data.repository;

import com.firma.data.model.RegistroCorreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroCorreoRepository extends JpaRepository<RegistroCorreo, Integer> {
}
