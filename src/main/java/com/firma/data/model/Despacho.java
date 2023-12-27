package com.firma.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "despacho")
public class Despacho {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = true)
    private String url;

    @Column(nullable = true)
    private LocalDate fechaconsulta;

    public Despacho(String nombre) {
        this.nombre = nombre;
    }
}
