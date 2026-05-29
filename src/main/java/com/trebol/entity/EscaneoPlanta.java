package com.trebol.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "escaneos_plantas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EscaneoPlanta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "nombre_planta")
    private String nombrePlanta;

    private String descripcion;

    private String imagen;

    private String resultado;

    private String estado;

    @Builder.Default
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}
