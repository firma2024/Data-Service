package com.firma.data.service.impl;

import com.firma.data.model.Usuario;
import com.firma.data.repository.UsuarioRepository;
import com.firma.data.service.intf.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> findAll() {
        return null;
    }

    @Override
    public Usuario findByName(String name) {
        return null;
    }

    @Override
    public Set<Usuario> findAllAbogadosByFirma(Integer firmaId, Integer rolId) {
        return usuarioRepository.findAllAbogadosByFirma(firmaId, rolId);
    }

    @Override
    public Integer numberAssignedProcesses(Integer usuarioId) {
        return usuarioRepository.getNumberAssignedProcesses(usuarioId);
    }
}
