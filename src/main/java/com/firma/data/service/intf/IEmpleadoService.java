package com.firma.data.service.intf;

import com.firma.data.model.Empleado;

public interface IEmpleadoService {
    Empleado saveEmpleado(Empleado empleado);
    Empleado findEmpleadoByUsuario(Integer usuarioId);
}
