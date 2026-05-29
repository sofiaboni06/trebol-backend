package com.trebol.service;

import com.trebol.dto.ServicioRequestDTO;
import com.trebol.dto.ServicioResponseDTO;

import java.util.List;

public interface ServicioService {

    List<ServicioResponseDTO> listar();

    ServicioResponseDTO buscarPorId(Long id);

    ServicioResponseDTO crear(ServicioRequestDTO request);

    ServicioResponseDTO actualizar(Long id, ServicioRequestDTO request);

    void eliminar(Long id);
}
