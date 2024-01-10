package com.firma.data.service.intf;

import com.firma.data.model.Despacho;

import java.util.List;

public interface IDespachoService {
    Despacho saveDespacho(Despacho despacho);
    List<Despacho> findAllDespachosWithOutLink();
    Despacho findDespachoByNombre(String nombre);
    Despacho updateDespacho(Despacho despacho);
    Despacho findDespachoById(Integer despachoId);

    Despacho findDespachoByProceso(Integer procesoId);
}
