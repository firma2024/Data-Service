package com.firma.data.payload.request;

import lombok.*;

import java.math.BigInteger;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProcesoRequest {
    private BigInteger idProceso;
    private String numeroRadicado;
    private String despacho;
    private String departamento;
    private String demandante;
    private String demandado;
    private String fechaRadicacion;
    private List<ActuacionRequest> actuaciones;
    private String tipoProceso;
    private String ubicacionExpediente;
    private Integer idFirma;
    private Integer idAbogado;
    private String estadoProceso;
}