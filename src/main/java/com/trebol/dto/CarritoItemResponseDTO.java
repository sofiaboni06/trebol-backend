package com.trebol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarritoItemResponseDTO {

    private Long id;
    private Long carritoId;
    private Long productoId;
    private Integer cantidad;
    private BigDecimal precio;
}
