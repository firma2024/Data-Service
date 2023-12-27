package com.firma.data.payload.response;

import com.firma.data.model.TipoAbogado;
import com.firma.data.model.Usuario;
import lombok.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioResponse {

    private Integer id;
    private String nombres;
    private String correo;
    private BigInteger telefono;
    private String img;
    private List<String> especialidades;

    public static List<UsuarioResponse> convertToResponse(List<Usuario> users) {
        List<UsuarioResponse> response = new ArrayList<>();
        for (Usuario user : users) {
            List<String> especialidades = new ArrayList<>();

            for(TipoAbogado tipoAbogado : user.getEspecialidadesAbogado()){
                especialidades.add(tipoAbogado.getNombre());
            }

            response.add(UsuarioResponse.builder()
                    .id(user.getId())
                    .nombres(user.getNombres())
                    .correo(user.getCorreo())
                    .telefono(user.getTelefono())
                    .img(user.getImg())
                    .especialidades(especialidades)
                    .build());
        }
        return response;
    }
}
