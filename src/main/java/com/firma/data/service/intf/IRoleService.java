package com.firma.data.service.intf;

import com.firma.data.model.Rol;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IRoleService {
    List<Rol> findAll();
    Rol findByName(String name);
    Rol saveRol(Rol rol);
}
