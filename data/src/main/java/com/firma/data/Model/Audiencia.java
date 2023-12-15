package com.firma.data.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "audiencia")
public class Audiencia {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String enlace;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Procesoid", nullable = false)
    private Proceso Procesoid;
}
