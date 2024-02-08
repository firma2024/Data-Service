package com.firma.data.service.intf;

import com.firma.data.model.Empleado;
import com.firma.data.model.Firma;
import com.firma.data.payload.request.FirmaRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFirmaService {
    ResponseEntity<?> saveFirma(FirmaRequest firma);
    ResponseEntity<?> findFirmaByUser(String userName);
    Firma findFirmaById(Integer id);
    Empleado saveEmpleado(Empleado empleado);
    Empleado findEmpleadoByUsuario(Integer idAbogado);
}
