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
    private BigInteger identificacion;
    private List<String> especialidades;
    private Integer numeroProcesosAsignados;
}
