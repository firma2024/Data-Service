package com.firma.data.service.intf;

import com.firma.data.model.Actuacion;

import java.util.List;

public interface IActuacionService {
    Actuacion saveActuacion(Actuacion actuacion);
    List<Actuacion> saveAllActuaciones(List<Actuacion> actuaciones);
    List<Actuacion> findByNoVisto(Integer firmaId);
    List<Actuacion> findAllByProceso(Integer procesoId);
}
