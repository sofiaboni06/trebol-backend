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
public class ConversacionIaResponseDTO {

    private Long id;
    private Long usuarioId;
    private String mensajeUsuario;
    private String mensajeIa;
    private String redirectUrl;
    private String contexto;
    private String modelo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
