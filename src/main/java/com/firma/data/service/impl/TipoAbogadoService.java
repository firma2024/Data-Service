package com.firma.data.service.impl;

import com.firma.data.model.TipoAbogado;
import com.firma.data.repository.TipoAbogadoRepository;
import com.firma.data.repository.TipoDocumentoRepository;
import com.firma.data.service.intf.ITipoAbogadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoAbogadoService implements ITipoAbogadoService {

    @Autowired
    private TipoAbogadoRepository tipoAbogadoRepository;

    @Override
    public List<TipoAbogado> findAll() {
        return tipoAbogadoRepository.findAll();
    }

    @Override
    public TipoAbogado findByName(String name) {
        return tipoAbogadoRepository.findByNombre(name);
    }

    @Override
    public TipoAbogado saveTipoAbogado(TipoAbogado tipoAbogado) {
        return tipoAbogadoRepository.save(tipoAbogado);
    }
}
