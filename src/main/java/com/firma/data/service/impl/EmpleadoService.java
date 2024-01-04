package com.firma.data.service.impl;

import com.firma.data.model.Empleado;
import com.firma.data.repository.EmpleadoRepository;
import com.firma.data.service.intf.IEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService implements IEmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado findEmpleadoByUsuario(Integer usuarioId) {
        return empleadoRepository.findEmpleadoByUsuario(usuarioId);
    }
}
