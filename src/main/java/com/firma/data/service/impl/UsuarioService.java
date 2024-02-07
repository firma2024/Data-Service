package com.firma.data.service.impl;

import com.firma.data.model.Usuario;
import com.firma.data.repository.UsuarioRepository;
import com.firma.data.service.intf.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Usuario findByUserName(String name) {
        return usuarioRepository.findByUsername(name);
    }

    @Override
    public Page<Usuario> findAllAbogadosByFirma(Integer firmaId, Integer rolId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("nombres").ascending());
        return usuarioRepository.findAllAbogadosByFirma(firmaId, rolId, paging);
    }

    @Override
    public Integer numberAssignedProcesses(Integer usuarioId) {
        Integer value = usuarioRepository.getNumberAssignedProcesses(usuarioId);
        return value == null ? 0 : value;
    }

    @Override
    public Page<Usuario> findAAbogadosByFilter(List<String> especialidades, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("nombres").ascending());
        return usuarioRepository.findAbogadosByFilter(especialidades , paging);
    }

    @Override
    public List<Usuario> findAllNamesAbogadosByFirma(Integer firmaId) {
        return usuarioRepository.findAllNamesAbogadosByFirma(firmaId);
    }
}
