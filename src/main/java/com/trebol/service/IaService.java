package com.trebol.service;

import com.trebol.dto.IaRequestDTO;
import com.trebol.dto.IaResponseDTO;
import java.util.List;

public interface IaService {
    IaResponseDTO preguntar(IaRequestDTO request);
    IaResponseDTO evaluarConsulta(IaRequestDTO request);
    List<String> temasPermitidos();
}
