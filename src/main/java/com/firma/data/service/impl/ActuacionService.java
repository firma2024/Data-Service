package com.firma.data.service.impl;

import com.firma.data.model.Actuacion;
import com.firma.data.repository.ActuacionRepository;
import com.firma.data.service.intf.IActuacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class ActuacionService implements IActuacionService {

    @Autowired
    private ActuacionRepository actuacionRepository;

    @Override
    public Actuacion saveActuacion(Actuacion actuacion) {
        return null;
    }

    @Override
    public List<Actuacion> saveAllActuaciones(List<Actuacion> actuaciones) {
        return actuacionRepository.saveAll(actuaciones);
    }

    @Override
    public List<Actuacion> findByNoVisto(Integer firmaId) {
        return actuacionRepository.findByNoVisto(firmaId);
    }

    @Override
    public Set<Actuacion> findAllByProceso(Integer procesoId) {
        return actuacionRepository.findAllByProceso(procesoId);
    }

    @Override
    public Set<Actuacion> findByFiltros(Integer procesoId, LocalDate fechaInicio, LocalDate fechaFin, String estadoActuacion) {
        return actuacionRepository.findByFiltros(procesoId, fechaInicio, fechaFin, estadoActuacion);
    }
}
