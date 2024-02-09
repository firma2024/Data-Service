package com.firma.data.service.intf;

import com.firma.data.payload.request.AudienciaRequest;
import com.firma.data.payload.request.EnlaceRequest;
import com.firma.data.payload.request.ProcesoRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProcessService {
    ResponseEntity<?> saveProcess(ProcesoRequest procesoRequest);
    ResponseEntity<?> findProcessJefe(Integer processId);
    ResponseEntity<?> findProcessAbogado(Integer processId);
    ResponseEntity<?> updateProcess(Integer id, ProcesoRequest procesoRequest);
    ResponseEntity<?> deleteProcess(Integer id);
    ResponseEntity<?> findProcessByFirmaFilter(Integer firmaId, String fechaInicioStr, String fechaFinStr, List<String> estadosProceso, String tipoProceso, Integer page, Integer size);
    ResponseEntity<?> findProcessByAbogadoFilter(Integer abogadoId, String fechaInicioStr, String fechaFinStr, List<String> estadosProceso, String tipoProceso, Integer page, Integer size);
    ResponseEntity<?> findAllProcesses();
    ResponseEntity<?> findAllByEstadoFirma(Integer firmaId, String name);
    ResponseEntity<?> findAllByEstadoAbogado(String userName, String name);
    ResponseEntity<?> saveAudiencia(AudienciaRequest audiencia);
    ResponseEntity<?> updateAudiencia(Integer id, String enlace);
    ResponseEntity<?> findAllEstadoProceso();
    ResponseEntity<?> findEstadoProcesoByName(String name);
    ResponseEntity<?> findAllTipoProceso();
    ResponseEntity<?> findTipoProcesoByName(String name);
    ResponseEntity<?> findAllDespachoWithOutLink(String year);
    ResponseEntity<?> findDespachoByProcess(Integer processId);
    ResponseEntity<?> saveEnlace(EnlaceRequest enlaceRequest);
}
