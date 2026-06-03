package com.trebol.service;

import com.trebol.dto.ProductoRequestDTO;
import com.trebol.dto.ProductoResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {

    ProductoResponseDTO crear(ProductoRequestDTO request);

    List<ProductoResponseDTO> listar();

    List<ProductoResponseDTO> buscar(
            String nombre,
            Long categoriaId,
            BigDecimal precioMin,
            BigDecimal precioMax
    );

    ProductoResponseDTO buscarPorId(Long id);

    ProductoResponseDTO actualizar(Long id, ProductoRequestDTO request);

    void eliminar(Long id);
}
