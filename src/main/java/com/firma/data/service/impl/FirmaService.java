package com.firma.data.service.impl;

import com.firma.data.model.Firma;
import com.firma.data.model.Usuario;
import com.firma.data.repository.FirmaRepository;
import com.firma.data.service.intf.IFirmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirmaService implements IFirmaService {

    @Autowired
    private FirmaRepository firmaRepository;

    @Override
    public Firma saveFirma(Firma firma) {
        return firmaRepository.save(firma);
    }

    @Override
    public List<Firma> findAll() {
        return firmaRepository.findAll();
    }

    @Override
    public Firma findById(Integer id) {
        return firmaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Firma> findByName(String name) {
        return firmaRepository.findByName(name);
    }
}
