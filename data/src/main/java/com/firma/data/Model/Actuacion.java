package com.firma.data.Model;

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

    @Column(nullable = false)
    private String anotacion;

    @Column(nullable = false)
    private LocalDate fechaActuacion;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @Column(columnDefinition = "text")
    private String documento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Procesoid", nullable = false)
    private Proceso Procesoid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EstadoActuacionid", nullable = false)
    private EstadoActuacion EstadoActuacionid;
}
