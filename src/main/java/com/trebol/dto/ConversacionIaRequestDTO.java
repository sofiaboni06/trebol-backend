package com.trebol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversacionIaRequestDTO {

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotBlank(message = "El mensaje del usuario es obligatorio")
    private String mensajeUsuario;

    private String mensajeIa;

    private String contexto;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;
}
