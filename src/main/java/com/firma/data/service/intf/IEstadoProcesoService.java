package com.firma.data.service.intf;

import com.firma.data.model.EstadoProceso;

import java.util.List;

public interface IEstadoProcesoService {
    List<EstadoProceso> findAll();
    EstadoProceso findByName(String name);
    EstadoProceso saveEstadoProceso(EstadoProceso estadoProceso);
}
