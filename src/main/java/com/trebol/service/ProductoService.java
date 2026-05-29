package com.trebol.service;

import com.trebol.dto.ProductoRequestDTO;
import com.trebol.dto.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {

    ProductoResponseDTO crear(ProductoRequestDTO request);

    List<ProductoResponseDTO> listar();

    ProductoResponseDTO buscarPorId(Long id);

    ProductoResponseDTO actualizar(Long id, ProductoRequestDTO request);

    void eliminar(Long id);
}
