package com.firma.data.service.impl;

import com.firma.data.model.Actuacion;
import com.firma.data.repository.ActuacionRepository;
import com.firma.data.service.intf.IActuacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return actuacionRepository.save(actuacion);
    }

    @Override
    public List<Actuacion> saveAllActuaciones(List<Actuacion> actuaciones) {
        return actuacionRepository.saveAll(actuaciones);
    }

    @Override
    public List<Actuacion> findByNoVisto(Integer procesoId) {
        return actuacionRepository.findByNoVisto(procesoId);
    }

    @Override
    public Page<Actuacion> findAllByProceso(Integer procesoId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return actuacionRepository.findAllByProceso(procesoId, paging);
    }

    @Override
    public Set<Actuacion> findAllByProcesoAndDocument(Integer procesoId) {
        return actuacionRepository.findAllByProcesoAndDocument(procesoId);
    }

    @Override
    public Page<Actuacion> findByFiltros(Integer procesoId, LocalDate fechaInicio, LocalDate fechaFin, String estadoActuacion, boolean existDocument, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return actuacionRepository.findByFiltros(procesoId, fechaInicio, fechaFin, estadoActuacion, existDocument, paging);
    }

    @Override
    public Set<Actuacion> findAllByNoSend() {
        return actuacionRepository.findAllByNoSend();
    }

    @Override
    public Actuacion findById(Integer id) {
        return actuacionRepository.findById(id).orElse(null);
    }

    @Override
    public Actuacion findLastActuacion(Integer procesoId) {
        return actuacionRepository.findLastActuacion(procesoId);
    }

    @Override
    public Actuacion update(Actuacion actuacion) {
        return actuacionRepository.save(actuacion);
    }
}
