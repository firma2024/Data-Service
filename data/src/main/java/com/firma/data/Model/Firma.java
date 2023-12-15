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
@Table(name = "firma")
public class Firma {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "abogado",
            joinColumns = {
                    @JoinColumn(name = "Usuarioid")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "Firmaid")
            }
    )
    private Set<Usuario> abogados = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "jefefirma",
            joinColumns = {
                    @JoinColumn(name = "Usuarioid")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "Firmaid")
            }
    )
    private Set<Usuario> jefesFirma = new HashSet<>();
}
