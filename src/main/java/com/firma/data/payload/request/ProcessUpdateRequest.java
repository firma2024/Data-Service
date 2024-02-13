package com.firma.data.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProcessUpdateRequest {
    private Integer id;
    private Integer idAbogado;
    private String estadoProceso;
}
