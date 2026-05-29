package com.trebol.service;

import com.trebol.dto.UsuarioResponseDTO;

import java.util.List;

public interface ClienteService {

    List<UsuarioResponseDTO> listarClientes();

    UsuarioResponseDTO obtenerClientePorId(Long id);
}
