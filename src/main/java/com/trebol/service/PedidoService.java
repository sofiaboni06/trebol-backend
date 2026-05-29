package com.trebol.service;

import com.trebol.dto.PedidoRequestDTO;
import com.trebol.dto.PedidoResponseDTO;

import java.util.List;

public interface PedidoService {

    List<PedidoResponseDTO> listar();

    PedidoResponseDTO buscarPorId(Long id);

    PedidoResponseDTO crear(PedidoRequestDTO request);

    PedidoResponseDTO actualizar(Long id, PedidoRequestDTO request);

    void eliminar(Long id);
}
