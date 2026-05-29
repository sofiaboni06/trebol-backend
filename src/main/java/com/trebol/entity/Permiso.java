package com.trebol.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permisos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private String descripcion;

    @ManyToMany(mappedBy = "permisos", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Rol> roles = new HashSet<>();
}
