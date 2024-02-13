package com.firma.data.service.intf;

import com.firma.data.model.EstadoActuacion;

import java.util.List;

public interface IEstadoActuacionService {
    List<EstadoActuacion> findAll();
    EstadoActuacion findByName(String name);
    EstadoActuacion saveEstadoActuacion(EstadoActuacion estadoActuacion);
}
