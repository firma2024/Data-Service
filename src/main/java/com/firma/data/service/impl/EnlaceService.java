package com.firma.data.service.impl;

import com.firma.data.model.Enlace;
import com.firma.data.repository.EnlaceRepository;
import com.firma.data.service.intf.IEnlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnlaceService implements IEnlaceService {

    @Autowired
    private EnlaceRepository enlaceRepository;

    @Override
    public Enlace saveEnlace(Enlace enlace) {
        return enlaceRepository.save(enlace);
    }

    @Override
    public Enlace findByDespachoAndYear(Integer despachoId, String year) {
        return enlaceRepository.findByDespachoAndYear(despachoId, year);
    }
}
