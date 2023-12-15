package com.firma.data.Repository;

import com.firma.data.Model.RegistroCorreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroCorreoRepository extends JpaRepository<RegistroCorreo, Integer> {
}
