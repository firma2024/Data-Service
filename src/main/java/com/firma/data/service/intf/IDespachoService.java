package com.firma.data.service.intf;

import com.firma.data.model.Despacho;

import java.util.List;

public interface IDespachoService {
    Despacho saveDespacho(Despacho despacho);
    List<Despacho> findAllDespachos();
    Despacho findDespachoByNombre(String nombre);
}
