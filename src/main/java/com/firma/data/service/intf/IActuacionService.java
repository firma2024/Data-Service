package com.firma.data.service.intf;

import com.firma.data.model.Actuacion;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IActuacionService {
    Actuacion saveActuacion(Actuacion actuacion);
    List<Actuacion> saveAllActuaciones(List<Actuacion> actuaciones);
    List<Actuacion> findByNoVisto(Integer procesoId);
    Page<Actuacion> findAllByProceso(Integer procesoId, Integer page, Integer size);
    Set<Actuacion> findAllByProcesoAndDocument(Integer procesoId);
    Page<Actuacion> findByFiltros(Integer procesoId, LocalDate fechaInicio, LocalDate fechaFin, String estadoActuacion, boolean existDocument, Integer page, Integer size);
    Set <Actuacion> findAllByNoSend();
    Actuacion findById(Integer id);
    Actuacion findLastActuacion(Integer procesoId);
    Actuacion update(Actuacion actuacion);
}
