package com.firma.data.service.impl;

import com.firma.data.model.TipoDocumento;
import com.firma.data.repository.TipoDocumentoRepository;
import com.firma.data.service.intf.ITipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDocumentoService implements ITipoDocumentoService {
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Override
    public List<TipoDocumento> findAll() {
        return tipoDocumentoRepository.findAll();
    }

    @Override
    public TipoDocumento findByName(String name) {
        return tipoDocumentoRepository.findByNombre(name);
    }

    @Override
    public TipoDocumento saveTipoDocumento(TipoDocumento tipoDocumento) {
        return tipoDocumentoRepository.save(tipoDocumento);
    }
}
