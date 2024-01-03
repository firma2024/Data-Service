package com.firma.data.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProcesoResponse {
    private Integer id;
    private String numeroRadicado;
    private String tipoProceso;
    private String fechaRadicacion;
}
