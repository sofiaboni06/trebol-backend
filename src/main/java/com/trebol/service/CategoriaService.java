package com.trebol.service;

import com.trebol.dto.CategoriaRequestDTO;
import com.trebol.dto.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {

    CategoriaResponseDTO crear(CategoriaRequestDTO request);

    List<CategoriaResponseDTO> listar();

    CategoriaResponseDTO buscarPorId(Long id);

    CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO request);

    void eliminar(Long id);
}