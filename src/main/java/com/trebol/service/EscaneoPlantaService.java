package com.trebol.service;

import com.trebol.dto.EscaneoPlantaRequestDTO;
import com.trebol.dto.EscaneoPlantaResponseDTO;

import java.util.List;

public interface EscaneoPlantaService {

    List<EscaneoPlantaResponseDTO> listar();

    EscaneoPlantaResponseDTO buscarPorId(Long id);

    EscaneoPlantaResponseDTO crear(EscaneoPlantaRequestDTO request);

    EscaneoPlantaResponseDTO actualizar(Long id, EscaneoPlantaRequestDTO request);

    void eliminar(Long id);
}
