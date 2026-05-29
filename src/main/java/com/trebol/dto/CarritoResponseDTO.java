package com.trebol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarritoResponseDTO {

    private Long id;
    private Long usuarioId;
    private Boolean estado;
    private LocalDateTime fechaCreacion;
    private List<CarritoItemResponseDTO> items;
}
