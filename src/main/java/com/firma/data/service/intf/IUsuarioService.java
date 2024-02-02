package com.firma.data.service.intf;

import com.firma.data.model.Usuario;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface IUsuarioService {
    Usuario save(Usuario usuario);
    Usuario update(Usuario usuario);
    Usuario findById(Integer id);
    List<Usuario> findAll();
    Usuario findByName(String name);
    Page<Usuario> findAllAbogadosByFirma(Integer firmaId, Integer rolId, Integer page, Integer size);
    Integer numberAssignedProcesses(Integer usuarioId);
    Page<Usuario> findAAbogadosByFilter(List<String> especialidades, Integer page, Integer size);
}
