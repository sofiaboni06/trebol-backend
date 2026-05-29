package com.trebol.service;

import com.trebol.dto.ConversacionIaRequestDTO;
import com.trebol.dto.ConversacionIaResponseDTO;

import java.util.List;

public interface ConversacionIaService {

    List<ConversacionIaResponseDTO> listar();

    ConversacionIaResponseDTO buscarPorId(Long id);

    ConversacionIaResponseDTO crear(ConversacionIaRequestDTO request);

    ConversacionIaResponseDTO actualizar(Long id, ConversacionIaRequestDTO request);

    void eliminar(Long id);
}
