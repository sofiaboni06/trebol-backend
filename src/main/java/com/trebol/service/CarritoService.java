package com.trebol.service;

import com.trebol.dto.CarritoItemRequestDTO;
import com.trebol.dto.CarritoItemResponseDTO;
import com.trebol.dto.CarritoRequestDTO;
import com.trebol.dto.CarritoResponseDTO;

import java.util.List;

public interface CarritoService {

    List<CarritoResponseDTO> listarCarritos();

    CarritoResponseDTO buscarCarritoPorId(Long id);

    CarritoResponseDTO crearCarrito(CarritoRequestDTO request);

    CarritoResponseDTO actualizarCarrito(Long id, CarritoRequestDTO request);

    void eliminarCarrito(Long id);

    List<CarritoItemResponseDTO> listarItems();

    CarritoItemResponseDTO buscarItemPorId(Long id);

    CarritoItemResponseDTO crearItem(CarritoItemRequestDTO request);

    CarritoItemResponseDTO actualizarItem(Long id, CarritoItemRequestDTO request);

    void eliminarItem(Long id);
}
