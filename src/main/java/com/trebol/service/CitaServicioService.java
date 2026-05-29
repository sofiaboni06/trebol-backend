package com.trebol.service;

import com.trebol.dto.CitaServicioRequestDTO;
import com.trebol.dto.CitaServicioResponseDTO;

import java.util.List;

public interface CitaServicioService {

    List<CitaServicioResponseDTO> listar();

    CitaServicioResponseDTO buscarPorId(Long id);

    CitaServicioResponseDTO crear(CitaServicioRequestDTO request);

    CitaServicioResponseDTO actualizar(Long id, CitaServicioRequestDTO request);

    void eliminar(Long id);
}
