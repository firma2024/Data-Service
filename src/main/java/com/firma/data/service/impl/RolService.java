package com.firma.data.service.impl;

import com.firma.data.model.Rol;
import com.firma.data.repository.RolRepository;
import com.firma.data.service.intf.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService implements IRoleService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @Override
    public Rol findByName(String name) {
        return rolRepository.findByNombre(name);
    }

    @Override
    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol);
    }
}
