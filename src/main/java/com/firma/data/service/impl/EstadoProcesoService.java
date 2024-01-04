package com.firma.data.service.impl;

import com.firma.data.model.EstadoProceso;
import com.firma.data.repository.EstadoProcesoRepository;
import com.firma.data.service.intf.IEstadoProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoProcesoService implements IEstadoProcesoService {

    @Autowired
    private EstadoProcesoRepository estadoProcesoRepository;

    @Override
    public List<EstadoProceso> findAll() {
        return estadoProcesoRepository.findAll();
    }

    @Override
    public EstadoProceso findByName(String name) {
        return estadoProcesoRepository.findByNombre(name);
    }

    @Override
    public EstadoProceso saveEstadoProceso(EstadoProceso estadoProceso) {
        return estadoProcesoRepository.save(estadoProceso);
    }
}
