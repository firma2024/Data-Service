package com.firma.data.service.impl;

import com.firma.data.model.Proceso;
import com.firma.data.repository.ProcesoRepository;
import com.firma.data.service.intf.IProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcesoService implements IProcesoService {

    @Autowired
    private ProcesoRepository procesoRepository;

    @Override
    public Proceso saveProceso(Proceso proceso) {
        return procesoRepository.save(proceso);
    }

    @Override
    public List<Proceso> findAllByFirma(Integer firmaId) {
        return procesoRepository.findAllByFirma(firmaId);
    }

    @Override
    public List<Proceso> findAllByAbogado(Integer abogadoId) {
        return null;
    }

    @Override
    public Proceso findById(Integer procesoId) {
        return procesoRepository.findById(procesoId).orElse(null);
    }
}
