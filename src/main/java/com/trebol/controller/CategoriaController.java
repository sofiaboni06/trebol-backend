package com.trebol.controller;

import com.trebol.dto.CategoriaRequestDTO;
import com.trebol.dto.CategoriaResponseDTO;
import com.trebol.service.CategoriaService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public CategoriaResponseDTO crear(
            @RequestBody CategoriaRequestDTO request
    ) {

        return categoriaService.crear(request);
    }

    @GetMapping
    public List<CategoriaResponseDTO> listar() {

        return categoriaService.listar();
    }

    @GetMapping("/{id}")
    public CategoriaResponseDTO buscarPorId(
            @PathVariable Long id
    ) {

        return categoriaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public CategoriaResponseDTO actualizar(
            @PathVariable Long id,
            @RequestBody CategoriaRequestDTO request
    ) {

        return categoriaService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Long id
    ) {

        categoriaService.eliminar(id);
    }
}