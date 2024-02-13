package com.firma.data.service.impl;

import com.firma.data.model.TipoProceso;
import com.firma.data.repository.TipoProcesoRepository;
import com.firma.data.service.intf.ITipoProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoProcesoService implements ITipoProcesoService {

    @Autowired
    private TipoProcesoRepository tipoProcesoRepository;

    @Override
    public List<TipoProceso> findAll() {
        return tipoProcesoRepository.findAll();
    }

    @Override
    public TipoProceso findByName(String name) {
        return tipoProcesoRepository.findByNombre(name);
    }

    @Override
    public TipoProceso saveTipoProceso(TipoProceso tipoProceso) {
        return tipoProcesoRepository.save(tipoProceso);
    }
}
