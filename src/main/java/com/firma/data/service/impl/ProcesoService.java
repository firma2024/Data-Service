package com.firma.data.service.impl;

import com.firma.data.model.Proceso;
import com.firma.data.repository.ProcesoRepository;
import com.firma.data.service.intf.IProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class ProcesoService implements IProcesoService {

    @Autowired
    private ProcesoRepository procesoRepository;

    @Override
    public Proceso saveProceso(Proceso proceso) {
        return procesoRepository.save(proceso);
    }

    @Override
    public Set<Proceso> findAllByFirma(Integer firmaId) {
        return procesoRepository.findAllByFirma(firmaId);
    }

    @Override
    public Set<Proceso> findAllByAbogado(Integer abogadoId) {
        return procesoRepository.findAllByAbogado(abogadoId);
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
    public Set<Proceso> findByFiltros(LocalDate fechaInicio, LocalDate fechaFin, List<String> estadosProceso, String tipoProceso) {
        return procesoRepository.findByFiltros(fechaInicio, fechaFin, estadosProceso, tipoProceso);
    }
}
