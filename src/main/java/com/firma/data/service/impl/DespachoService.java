package com.firma.data.service.impl;

import com.firma.data.model.Despacho;
import com.firma.data.repository.DespachoRepository;
import com.firma.data.service.intf.IDespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespachoService implements IDespachoService {

    @Autowired
    private DespachoRepository despachoRepository;

    @Override
    public Despacho saveDespacho(Despacho despacho) {
        return despachoRepository.save(despacho);
    }

    @Override
    public List<Despacho> findAllDespachosWithOutLink() {
        return despachoRepository.findAllDespachosWithOutLink();
    }

    @Override
    public Despacho findDespachoByNombre(String nombre) {
        return despachoRepository.findDespachoByNombre(nombre);
    }

    @Override
    public Despacho updateDespacho(Despacho despacho) {
        return despachoRepository.save(despacho);
    }

    @Override
    public Despacho findDespachoById(Integer despachoId) {
        return despachoRepository.findById(despachoId).orElse(null);
    }

    @Override
    public Despacho findDespachoByProceso(Integer procesoId) {
        return despachoRepository.findDespachoByProceso(procesoId);
    }
}
