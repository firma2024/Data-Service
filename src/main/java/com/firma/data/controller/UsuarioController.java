package com.firma.data.controller;

import com.firma.data.model.Firma;
import com.firma.data.model.Rol;
import com.firma.data.model.TipoAbogado;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        Set<Usuario> users = usuarioService.findAllAbogadosByFirma(firmaId, role.getId());
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<UsuarioResponse> userResponse = new ArrayList<>();

        for (Usuario user : users) {
            int number = usuarioService.numberAssignedProcesses(user.getId());
            List<String> especialidades = new ArrayList<>();

            for(TipoAbogado tipoAbogado : user.getEspecialidadesAbogado()){
                especialidades.add(tipoAbogado.getNombre());
            }
            userResponse.add(UsuarioResponse.builder()
                    .id(user.getId())
                    .nombres(user.getNombres())
                    .correo(user.getCorreo())
                    .telefono(user.getTelefono())
                    .img(user.getImg())
                    .especialidades(especialidades)
                    .numeroProcesosAsignados(number)
                    .build());

        }

        Firma firma = firmaService.findById(firmaId);
        FirmaUsuariosResponse response = FirmaUsuariosResponse.builder()
                .id(firma.getId())
                .nombre(firma.getNombre())
                .direccion(firma.getDireccion())
                .usuarios(userResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
