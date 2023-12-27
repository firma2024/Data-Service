package com.firma.data.service.intf;

import com.firma.data.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> findAll();
    Usuario findByName(String name);
    List<Usuario> findAllAbogadosByFirma(Integer firmaId, Integer rolId);
}
