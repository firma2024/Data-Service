package com.firma.data.payload.request;

import com.firma.data.model.Actuacion;
import com.firma.data.model.Proceso;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProcessRequest {
    private Proceso process;
    List<Actuacion> actions;
}