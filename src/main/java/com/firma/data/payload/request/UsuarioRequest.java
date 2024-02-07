package com.firma.data.payload.request;

import lombok.*;

import java.math.BigInteger;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioRequest {
    private Integer id;
    private String nombres;
    private String correo;
    private BigInteger telefono;
    private BigInteger identificacion;
    private String username;
    private String tipoDocumento;
    private Set<String> especialidades;
    private Integer firmaId;
}
