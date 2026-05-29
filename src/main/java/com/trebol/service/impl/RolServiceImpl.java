package com.trebol.service.impl;

import com.trebol.dto.RolRequestDTO;
import com.trebol.dto.RolResponseDTO;
import com.trebol.entity.Rol;
import com.trebol.repository.RolRepository;
import com.trebol.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    public List<RolResponseDTO> listar() {
        return rolRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    public RolResponseDTO buscarPorId(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        return mapear(rol);
    }

    @Override
    public RolResponseDTO crear(RolRequestDTO request) {
        Rol rol = new Rol();
        rol.setNombre(request.getNombre());

        Rol guardado = rolRepository.save(rol);
        return mapear(guardado);
    }

    @Override
    public RolResponseDTO actualizar(Long id, RolRequestDTO request) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        rol.setNombre(request.getNombre());
        Rol actualizado = rolRepository.save(rol);
        return mapear(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new IllegalArgumentException("Rol no encontrado");
        }
        rolRepository.deleteById(id);
    }

    private RolResponseDTO mapear(Rol rol) {
        return RolResponseDTO.builder()
                .id(rol.getId())
                .nombre(rol.getNombre())
                .build();
    }
}
