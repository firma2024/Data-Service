package com.firma.data.service.intf;

import com.firma.data.model.TipoAbogado;

import java.util.List;

public interface ITipoAbogadoService {
    List<TipoAbogado> findAll();
    TipoAbogado findByName(String name);
    TipoAbogado saveTipoAbogado(TipoAbogado tipoAbogado);
}
