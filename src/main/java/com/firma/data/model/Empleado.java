package com.firma.data.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "despacho")
public class Empleado {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Usuarioid", nullable = false)
    private Usuario Usuarioid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Firmaid", nullable = false)
    private Firma Firmaid;
}
