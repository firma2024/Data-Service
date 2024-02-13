package com.firma.data.payload.request;

import com.firma.data.model.Empleado;
import com.firma.data.model.Firma;
import com.firma.data.model.Usuario;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioRequest {
    private Usuario user;
    private Empleado employee;
}
