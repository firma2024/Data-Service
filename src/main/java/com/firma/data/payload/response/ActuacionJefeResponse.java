package com.firma.data.payload.response;

import lombok.*;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ActuacionJefeResponse {
    private String nombreActuacion;
    private String anotacion;
    private String fechaActuacion;
    private String fechaRegistro;
    private boolean existDocument;
    private String estado;
}
