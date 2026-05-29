package com.trebol.service;

import com.trebol.dto.RolRequestDTO;
import com.trebol.dto.RolResponseDTO;

import java.util.List;

public interface RolService {

    List<RolResponseDTO> listar();

    RolResponseDTO buscarPorId(Long id);

    RolResponseDTO crear(RolRequestDTO request);

    RolResponseDTO actualizar(Long id, RolRequestDTO request);

    void eliminar(Long id);
}
