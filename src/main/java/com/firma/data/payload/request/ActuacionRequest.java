package com.firma.data.payload.request;

import lombok.*;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ActuacionRequest {
    private String nombreActuacion;
    private String anotacion;
    private String fechaActuacion;
    private String fechaRegistro;
    private String proceso;
    private boolean existDocument;
}