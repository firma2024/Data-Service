package com.firma.data.service.intf;

import com.firma.data.model.Proceso;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IProcesoService {
    Set<Proceso> findAll();
    Proceso saveProceso(Proceso proceso);
    Proceso findById(Integer procesoId);
    Proceso updateProceso(Proceso proceso);
    Page<Proceso> findByFiltros(LocalDate fechaInicio, LocalDate fechaFin, List<String> estadosProceso, String tipoProceso, Integer page, Integer size, Integer firmaId);
    Page<Proceso> findAllByAbogado(Integer abogadoId, String fechaInicioStr, String fechaFinStr, List<String> estadosProceso, String tipoProceso, Integer page, Integer size);
    Proceso findByRadicado(String radicado);
    Set<Proceso> findAllByFirmaAndEstado(Integer firmaId, String estadoProceso);
    Set<Proceso> findAllByAbogadoAndEstado(String userName, String name);
}
