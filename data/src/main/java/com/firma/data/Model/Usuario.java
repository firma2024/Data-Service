package com.firma.data.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

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
    private String password;

    @Column(nullable = false)
    private BigInteger telefono;

    @Column(nullable = false)
    private BigInteger identificacion;

    @Column(nullable = false)
    private String correo;

    @Column(columnDefinition = "text")
    private String img;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Rolid", nullable = false)
    private Rol Rolid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TipoDocumentoid", nullable = false)
    private TipoDocumento TipoDocumentoid;
}
