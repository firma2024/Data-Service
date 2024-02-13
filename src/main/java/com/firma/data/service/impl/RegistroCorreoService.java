package com.firma.data.service.impl;

import com.firma.data.model.RegistroCorreo;
import com.firma.data.repository.RegistroCorreoRepository;
import com.firma.data.service.intf.IRegistroCorreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroCorreoService implements IRegistroCorreoService {

    @Autowired
    private RegistroCorreoRepository registroCorreoRepository;

    @Override
    public RegistroCorreo save(RegistroCorreo registroCorreo) {
        return registroCorreoRepository.save(registroCorreo);
    }
}
