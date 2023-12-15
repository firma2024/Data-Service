package com.firma.data.Repository;

import com.firma.data.Model.Actuacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActuacionRepository extends JpaRepository<Actuacion, Integer> {
}