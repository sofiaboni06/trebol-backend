package com.trebol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioRequestDTO {

    @NotBlank(message = "El nombre del servicio es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción del servicio es obligatoria")
    private String descripcion;

    @NotNull(message = "El precio base es obligatorio")
    private BigDecimal precioBase;

    @NotBlank(message = "La imagen del servicio es obligatoria")
    private String imagen;

    @NotNull(message = "El estado del servicio es obligatorio")
    private Boolean estado;
}
