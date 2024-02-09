package com.firma.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "actuacion")
public class Actuacion {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String actuacion;

    @Column(nullable = true)
    private String anotacion;

    @Column(nullable = false)
    private LocalDate fechaactuacion;

    @Column(nullable = false)
    private LocalDate fecharegistro;

    @Column(nullable = true)
    private LocalDate fechainicia;

    @Column(nullable = true)
    private LocalDate fechafinaliza;

    @Column(nullable = false)
    private Boolean existedoc;
    @Lob
    @Column(nullable = true, length = 10000000)
    private byte[] documento;

    @Column(nullable = false)
    private Character enviado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "procesoid", nullable = false)
    private Proceso proceso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estadoactuacionid", nullable = false)
    private EstadoActuacion estadoactuacion;
}
