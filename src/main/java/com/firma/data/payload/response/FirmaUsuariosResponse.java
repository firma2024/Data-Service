package com.firma.data.payload.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FirmaUsuariosResponse {
    private Integer id;
    private String nombre;
    private String direccion;
    private List<UsuarioResponse> usuarios;
}
