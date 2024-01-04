package com.firma.data.service.intf;

import com.firma.data.model.Proceso;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IProcesoService {
    Set<Proceso> findAll();
    Proceso saveProceso(Proceso proceso);
    Set<Proceso> findAllByFirma(Integer firmaId);
    Proceso findById(Integer procesoId);
    Proceso updateProceso(Proceso proceso);
    Set<Proceso> findByFiltros(LocalDate fechaInicio, LocalDate fechaFin, List<String> estadosProceso, String tipoProceso);
    Set<Proceso> findAllByAbogado(Integer abogadoId);
    Proceso findByRadicado(String radicado);
    Set<Proceso> findAllByFirmaAndEstado(Integer firmaId, String estadoProceso);

    Set<Proceso> findAllByAbogadoAndEstado(Integer abogadoId, String name);
}
