package com.firma.data.service.intf;

import com.firma.data.model.Actuacion;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IActuacionService {
    Actuacion saveActuacion(Actuacion actuacion);
    List<Actuacion> saveAllActuaciones(List<Actuacion> actuaciones);
    List<Actuacion> findByNoVisto(Integer procesoId);
    Set<Actuacion> findAllByProceso(Integer procesoId);
    Set<Actuacion> findAllByProcesoAndDocument(Integer procesoId);
    Set<Actuacion> findByFiltros(Integer procesoId, LocalDate fechaInicio, LocalDate fechaFin, String estadoActuacion, boolean existDocument);
    Set <Actuacion> findAllByNoSend();
    Actuacion findById(Integer id);
    Actuacion findLastActuacion(Integer procesoId);
    Actuacion update(Actuacion actuacion);
}
