package com.firma.data.service.intf;

import com.firma.data.model.Actuacion;
import com.firma.data.model.EstadoActuacion;
import com.firma.data.model.RegistroCorreo;
import com.firma.data.payload.request.ActuacionRequest;
import com.firma.data.payload.request.EnlaceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

public interface IActuacionService {
    ResponseEntity<?> saveActuaciones(Set <Actuacion> actuacionRequest);
    ResponseEntity<?> findAllActuacionesNotSend();
    ResponseEntity <?> updateActuacionSend(Actuacion actuacion);
    ResponseEntity<?> findActuacionesByProcessJefeFilter(Integer processId, String fechaInicioStr, String fechaFinStr, String estadoActuacion, Integer page, Integer size);
    ResponseEntity<?> findActuacionesByProcessAbogadoFilter(Integer processId, String fechaInicioStr, String fechaFinStr, Boolean existDoc, Integer page, Integer size);
    ResponseEntity<?> findActuacion(Integer actuacionId);
    Actuacion findById(Integer actuacionId);
    void update(Actuacion actuacion);
    Set<Actuacion> findAllByProcesoAndDocument(Integer procesoId);
    ResponseEntity<?> findAllEstadoActuacion();
    ResponseEntity<?> findEstadoActuacionByName(String name);
    void saveAllActuaciones(List<Actuacion> actuaciones);
    List<Actuacion> findByNoVisto(Integer id);
    ResponseEntity<?>  findLastActuacion(Integer id);
    ResponseEntity<?> saveActuacion(Actuacion actuacionRequest);
    ResponseEntity<?> saveRegistroCorreo(RegistroCorreo registroCorreo);
}
