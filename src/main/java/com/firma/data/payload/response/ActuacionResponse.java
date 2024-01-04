package com.firma.data.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ActuacionResponse {
    private Integer id;
    private String anotacion;
    private String actuacion;
    private String radicado;
    private String demandante;
    private String demandado;
    private String emailAbogado;
    private String fechaActuacion;
    private String fechaRegistro;
    private boolean existeDocumento;
    private String tipoProceso;
    private String despacho;
}
