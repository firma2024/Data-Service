package com.firma.data.service.intf;

import com.firma.data.model.TipoDocumento;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ITipoDocumentoService {
    List<TipoDocumento> findAll();
    TipoDocumento findByName(String name);
    TipoDocumento saveTipoDocumento(TipoDocumento tipoDocumento);
}
