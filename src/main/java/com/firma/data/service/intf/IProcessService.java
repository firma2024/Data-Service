package com.firma.data.service.intf;

import com.firma.data.model.Audiencia;
import com.firma.data.model.Enlace;
import com.firma.data.model.Proceso;
import com.firma.data.payload.request.ProcessRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProcessService {
    ResponseEntity<?> saveProcess(ProcessRequest procesoRequest);
    ResponseEntity<?> findProcessById(Integer processId);
    ResponseEntity<?> updateProcess(Proceso process);
    ResponseEntity<?> deleteProcess(Integer id);
    ResponseEntity<?> findProcessByFirmaFilter(Integer firmaId, String fechaInicioStr, String fechaFinStr, List<String> estadosProceso, String tipoProceso, Integer page, Integer size);
    ResponseEntity<?> findProcessByAbogadoFilter(Integer abogadoId, String fechaInicioStr, String fechaFinStr, List<String> estadosProceso, String tipoProceso, Integer page, Integer size);
    ResponseEntity<?> findAllProcesses();
    ResponseEntity<?> findAllByEstadoFirma(Integer firmaId, String name);
    ResponseEntity<?> findAllByEstadoAbogado(String userName, String name);
    ResponseEntity<?> saveAudiencia(Audiencia audiencia);
    ResponseEntity<?> updateAudiencia(Integer id, String enlace);
    ResponseEntity<?> findAllEstadoProceso();
    ResponseEntity<?> findEstadoProcesoByName(String name);
    ResponseEntity<?> findAllTipoProceso();
    ResponseEntity<?> findTipoProcesoByName(String name);
    ResponseEntity<?> findAllDespachoWithOutLink(String year);
    ResponseEntity<?> findDespachoByProcess(Integer processId);
    ResponseEntity<?> saveEnlace(Enlace enlace);
    ResponseEntity<?> findAllAudienciasByProceso(Integer procesoId);
    ResponseEntity<?> findDespachoById(Integer id);
    ResponseEntity<?> findProcessByRadicado(String radicado);
    ResponseEntity<?> findEnlaceByDespachoAndYear(Integer id, String year);
    ResponseEntity<?> findDespachoByName(String name);
}
