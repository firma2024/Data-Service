package com.firma.data.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "abogado")
public class Abogado {
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "especialidadabogado",
            joinColumns = {
                    @JoinColumn(name = "Abogadoid")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "TipoAbogadoid")
            }
    )
    private Set<TipoAbogado> especialidadesAbogado = new HashSet<>();
}
