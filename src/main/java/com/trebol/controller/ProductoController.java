package com.trebol.controller;

import com.trebol.dto.ProductoRequestDTO;
import com.trebol.dto.ProductoResponseDTO;
import com.trebol.service.ProductoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(
            @RequestBody ProductoRequestDTO request
    ) {
        ProductoResponseDTO response = productoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        List<ProductoResponseDTO> response = productoService.listar();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {
        ProductoResponseDTO response = productoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody ProductoRequestDTO request
    ) {
        ProductoResponseDTO response = productoService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
