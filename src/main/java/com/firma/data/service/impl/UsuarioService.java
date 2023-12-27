package com.firma.data.service.impl;

import com.firma.data.model.Usuario;
import com.firma.data.repository.UsuarioRepository;
import com.firma.data.service.intf.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return null;
    }

    @Override
    public Usuario findByName(String name) {
        return null;
    }

    @Override
    public List<Usuario> findAllAbogadosByFirma(Integer firmaId, Integer rolId) {
        return usuarioRepository.findAllAbogadosByFirma(firmaId, rolId);
    }
}
