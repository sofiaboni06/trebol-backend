package com.trebol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EscaneoPlantaResponseDTO {

    private Long id;
    private Long usuarioId;
    private String nombrePlanta;
    private String descripcion;
    private String imagen;
    private String resultado;
    private String estado;
    private LocalDateTime fechaCreacion;
}
