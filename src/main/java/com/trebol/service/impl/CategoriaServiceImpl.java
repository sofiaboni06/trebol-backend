package com.trebol.service.impl;

import com.trebol.dto.CategoriaRequestDTO;
import com.trebol.dto.CategoriaResponseDTO;
import com.trebol.entity.Categoria;
import com.trebol.exception.ResourceNotFoundException;
import com.trebol.repository.CategoriaRepository;
import com.trebol.service.CategoriaService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public CategoriaResponseDTO crear(CategoriaRequestDTO request) {

        Categoria categoria = Categoria.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .imagen(request.getImagen())
                .build();

        categoria = categoriaRepository.save(categoria);

        return mapear(categoria);
    }

    @Override
    public List<CategoriaResponseDTO> listar() {

        return categoriaRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    public CategoriaResponseDTO buscarPorId(Long id) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));

        return mapear(categoria);
    }

    @Override
    public CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO request) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));

        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        categoria.setImagen(request.getImagen());

        categoria = categoriaRepository.save(categoria);

        return mapear(categoria);
    }

    @Override
    public void eliminar(Long id) {

        categoriaRepository.deleteById(id);
    }

    private CategoriaResponseDTO mapear(Categoria categoria) {

        return CategoriaResponseDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .imagen(categoria.getImagen())
                .build();
    }
}