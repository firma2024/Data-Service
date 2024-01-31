package com.firma.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "usuario")
public class Usuario {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private BigInteger telefono;

    @Column(nullable = false)
    private BigInteger identificacion;

    @Column(nullable = false)
    private String correo;

    @Lob
    @Column(length = 1000)
    private byte[] img;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rolid", nullable = false)
    private Rol rol;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipodocumentoid", nullable = false)
    private TipoDocumento tipodocumento;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "especialidadabogado",
            joinColumns = {
                    @JoinColumn(name = "usuarioid")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "tipoabogadoid")
            }
    )
    private Set<TipoAbogado> especialidadesAbogado = new HashSet<>();
}
