package com.firma.data.service.impl;

import com.firma.data.model.EstadoActuacion;
import com.firma.data.repository.EstadoActuacionRepository;
import com.firma.data.service.intf.IEstadoActuacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoActuacionService implements IEstadoActuacionService {

    @Autowired
    private EstadoActuacionRepository estadoActuacionRepository;

    @Override
    public List<EstadoActuacion> findAll() {
        return estadoActuacionRepository.findAll();
    }

    @Override
    public EstadoActuacion findByName(String name) {
        return estadoActuacionRepository.findByNombre(name);
    }

    @Override
    public EstadoActuacion saveEstadoActuacion(EstadoActuacion estadoActuacion) {
        return estadoActuacionRepository.save(estadoActuacion);
    }
}
