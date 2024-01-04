package com.firma.data.service.intf;

import com.firma.data.model.Usuario;

import java.util.List;
import java.util.Set;

public interface IUsuarioService {
    Usuario save(Usuario usuario);
    Usuario update(Usuario usuario);
    Usuario findById(Integer id);
    List<Usuario> findAll();
    Usuario findByName(String name);
    Set<Usuario> findAllAbogadosByFirma(Integer firmaId, Integer rolId);
    Integer numberAssignedProcesses(Integer usuarioId);
}
