package com.firma.data.payload.request;

import com.firma.data.model.TipoAbogado;
import com.firma.data.model.TipoDocumento;
import lombok.*;

import java.math.BigInteger;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequest {
    private Integer id;
    private String nombres;
    private String correo;
    private BigInteger telefono;
    private BigInteger identificacion;
    private String username;
    private TipoDocumento tipoDocumento;
    private Set<TipoAbogado> especialidades;
    private Integer firmaId;
}