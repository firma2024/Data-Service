package com.firma.data.controller;

import com.firma.data.model.Firma;
import com.firma.data.model.Rol;
import com.firma.data.model.Usuario;
import com.firma.data.payload.response.FirmaUsuariosResponse;
import com.firma.data.payload.response.UsuarioResponse;
import com.firma.data.service.intf.IFirmaService;
import com.firma.data.service.intf.IRoleService;
import com.firma.data.service.intf.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/data/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IFirmaService firmaService;

    @GetMapping("/get/abogados")
    public ResponseEntity<?> getFirmaAbogados(@RequestParam Integer firmaId) {
        Rol role = roleService.findByName("ABOGADO");
        List<Usuario> users = usuarioService.findAllAbogadosByFirma(firmaId, role.getId());
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Firma firma = firmaService.findById(firmaId);
        FirmaUsuariosResponse response = FirmaUsuariosResponse.builder()
                .id(firma.getId())
                .nombre(firma.getNombre())
                .direccion(firma.getDireccion())
                .usuarios(UsuarioResponse.convertToResponse(users))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
