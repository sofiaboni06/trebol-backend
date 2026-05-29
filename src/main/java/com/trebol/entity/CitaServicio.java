package com.trebol.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "citas_servicios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    private LocalDate fecha;
    private LocalTime hora;
    private String direccion;
    private String descripcionCliente;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoCita estado = EstadoCita.PENDIENTE;

    public enum EstadoCita {
        PENDIENTE, CONFIRMADA, FINALIZADA, CANCELADA
    }
}
