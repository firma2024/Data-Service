package com.firma.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;

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
    private BigInteger radicado;

    @Column(nullable = false)
    private BigInteger numeroProceso;

    @Column(nullable = false)
    private String demandado;

    @Column(nullable = false)
    private String demandante;

    @Column(nullable = false)
    private LocalDate fechaRadicado;

    @Column(nullable = false)
    private String ubicacionExpediente;

    @Column(nullable = false)
    private Character eliminado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Despachoid", nullable = false)
    private Despacho Despachoid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TipoProcesoid", nullable = false)
    private TipoProceso TipoProcesoid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EstadoProcesoid", nullable = false)
    private EstadoProceso EstadoProcesoid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Firmaid", nullable = false)
    private Firma Firmaid;

}
