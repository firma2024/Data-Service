package com.firma.data.service.impl;

import com.firma.data.model.Proceso;
import com.firma.data.repository.ProcesoRepository;
import com.firma.data.service.intf.IProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class ProcesoService implements IProcesoService {

    @Autowired
    private ProcesoRepository procesoRepository;

    @Override
    public Set<Proceso> findAll() {
        return procesoRepository.findAllProcesos();
    }

    @Override
    public Proceso saveProceso(Proceso proceso) {
        return procesoRepository.save(proceso);
    }

    @Override
    public Set<Proceso> findAllByFirma(Integer firmaId) {
        return procesoRepository.findAllByFirma(firmaId);
    }

    @Override
    public Page<Proceso> findAllByAbogado(Integer abogadoId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return procesoRepository.findAllByAbogado(abogadoId, paging);
    }

    @Override
    public Proceso findByRadicado(String radicado) {
        return procesoRepository.findByRadicado(radicado);
    }

    @Override
    public Set<Proceso> findAllByFirmaAndEstado(Integer firmaId, String estadoProceso) {
        return procesoRepository.findAllByFirmaAndEstado(firmaId, estadoProceso);
    }

    @Override
    public Set<Proceso> findAllByAbogadoAndEstado(Integer abogadoId, String name) {
        return procesoRepository.findAllByAbogadoAndEstado(abogadoId, name);
    }

    @Override
    public Proceso findById(Integer procesoId) {
        return procesoRepository.findById(procesoId).orElse(null);
    }

    @Override
    public Proceso updateProceso(Proceso proceso) {
        return procesoRepository.save(proceso);
    }

    @Override
    public Page<Proceso> findByFiltros(LocalDate fechaInicio, LocalDate fechaFin, List<String> estadosProceso, String tipoProceso, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return procesoRepository.findByFiltros(fechaInicio, fechaFin, estadosProceso, tipoProceso, paging);
    }
}
