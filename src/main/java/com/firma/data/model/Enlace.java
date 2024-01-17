package com.firma.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "enlace")
public class Enlace {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private String url;

    @Column(nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaconsulta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "despachoid", nullable = false)
    private Despacho despacho;
}
