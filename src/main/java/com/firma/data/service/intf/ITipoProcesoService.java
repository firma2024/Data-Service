package com.firma.data.service.intf;

import com.firma.data.model.TipoProceso;

import java.util.List;

public interface ITipoProcesoService {
    List<TipoProceso> findAll();
    TipoProceso findByName(String name);
    TipoProceso saveTipoProceso(TipoProceso tipoProceso);
}
