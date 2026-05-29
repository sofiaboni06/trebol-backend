package com.trebol.dto;

import com.trebol.entity.CitaServicio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaServicioResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long servicioId;
    private LocalDate fecha;
    private LocalTime hora;
    private String direccion;
    private String descripcionCliente;
    private CitaServicio.EstadoCita estado;
}
