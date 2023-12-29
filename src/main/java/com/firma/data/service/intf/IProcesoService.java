package com.firma.data.service.intf;

import com.firma.data.model.Proceso;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IProcesoService {
    Proceso saveProceso(Proceso proceso);
    Set<Proceso> findAllByFirma(Integer firmaId);
    List<Proceso> findAllByAbogado(Integer abogadoId);
    Proceso findById(Integer procesoId);
    Proceso updateProceso(Proceso proceso);
    Set<Proceso> findByFiltros(LocalDate fechaInicio, LocalDate fechaFin, List<String> estadosProceso, String tipoProceso);
}
