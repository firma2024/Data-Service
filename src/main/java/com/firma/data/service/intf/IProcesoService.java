package com.firma.data.service.intf;

import com.firma.data.model.Proceso;

import java.util.List;

public interface IProcesoService {
    Proceso saveProceso(Proceso proceso);
    List<Proceso> findAllByFirma(Integer firmaId);
    List<Proceso> findAllByAbogado(Integer abogadoId);
    Proceso findById(Integer procesoId);
}
