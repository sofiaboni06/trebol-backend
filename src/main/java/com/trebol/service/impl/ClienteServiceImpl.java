package com.trebol.service.impl;

import com.trebol.dto.UsuarioResponseDTO;
import com.trebol.entity.Rol;
import com.trebol.entity.Usuario;
import com.trebol.repository.RolRepository;
import com.trebol.repository.UsuarioRepository;
import com.trebol.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private static final String ROL_CLIENTE = "CLIENTE";
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Override
    public List<UsuarioResponseDTO> listarClientes() {
        Rol rol = rolRepository.findByNombre(ROL_CLIENTE)
                .orElseThrow(() -> new IllegalArgumentException("Rol CLIENTE no encontrado"));

        return usuarioRepository.findByRolesNombre(rol.getNombre())
                .stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    public UsuarioResponseDTO obtenerClientePorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        boolean esCliente = usuario.getRoles()
                .stream()
                .anyMatch(rol -> ROL_CLIENTE.equals(rol.getNombre()));

        if (!esCliente) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }

        return mapear(usuario);
    }

    private UsuarioResponseDTO mapear(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .build();
    }
}
