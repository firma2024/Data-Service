package com.firma.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "proceso")
public class Proceso {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String radicado;

    @Column(nullable = false)
    private BigInteger numeroproceso;

    @Column(nullable = false)
    private String sujetos;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecharadicado;

    private String ubicacionexpediente;

    @Column(nullable = false)
    private Character eliminado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "despachoid", nullable = false)
    private Despacho despacho;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipoprocesoid", nullable = false)
    private TipoProceso tipoproceso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estadoprocesoid", nullable = false)
    private EstadoProceso estadoproceso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empleadoid", nullable = false)
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "firmaid", nullable = false)
    private Firma firma;

}
