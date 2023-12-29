package com.firma.data.service.intf;

import com.firma.data.model.Usuario;

import java.util.List;
import java.util.Set;

public interface IUsuarioService {
    List<Usuario> findAll();
    Usuario findByName(String name);
    Set<Usuario> findAllAbogadosByFirma(Integer firmaId, Integer rolId);
    int numberAssignedProcesses(Integer usuarioId);
}
