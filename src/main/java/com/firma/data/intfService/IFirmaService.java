package com.firma.data.intfService;

import com.firma.data.model.Empleado;
import com.firma.data.payload.request.FirmaRequest;
import org.springframework.http.ResponseEntity;

public interface IFirmaService {
    ResponseEntity<?> saveFirma(FirmaRequest firma);
    ResponseEntity<?> findFirmaByUser(String userName);
    ResponseEntity<?> findFirmaById(Integer id);
    Empleado saveEmpleado(Empleado empleado);
    ResponseEntity<?> findEmpleadoByUsuario(Integer idAbogado);
}
