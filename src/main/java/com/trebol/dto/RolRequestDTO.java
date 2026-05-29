package com.trebol.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolRequestDTO {

    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombre;
}
