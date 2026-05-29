package com.trebol.controller;

import com.trebol.dto.CarritoItemRequestDTO;
import com.trebol.dto.CarritoItemResponseDTO;
import com.trebol.dto.CarritoRequestDTO;
import com.trebol.dto.CarritoResponseDTO;
import com.trebol.service.CarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping("/carritos")
    public ResponseEntity<List<CarritoResponseDTO>> listarCarritos() {
        return ResponseEntity.ok(carritoService.listarCarritos());
    }

    @GetMapping("/carritos/{id}")
    public ResponseEntity<CarritoResponseDTO> buscarCarritoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(carritoService.buscarCarritoPorId(id));
    }

    @PostMapping("/carritos")
    public ResponseEntity<CarritoResponseDTO> crearCarrito(@Valid @RequestBody CarritoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.crearCarrito(request));
    }

    @PutMapping("/carritos/{id}")
    public ResponseEntity<CarritoResponseDTO> actualizarCarrito(
            @PathVariable Long id,
            @Valid @RequestBody CarritoRequestDTO request
    ) {
        return ResponseEntity.ok(carritoService.actualizarCarrito(id, request));
    }

    @DeleteMapping("/carritos/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Long id) {
        carritoService.eliminarCarrito(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/carrito-items")
    public ResponseEntity<List<CarritoItemResponseDTO>> listarItems() {
        return ResponseEntity.ok(carritoService.listarItems());
    }

    @GetMapping("/carrito-items/{id}")
    public ResponseEntity<CarritoItemResponseDTO> buscarItemPorId(@PathVariable Long id) {
        return ResponseEntity.ok(carritoService.buscarItemPorId(id));
    }

    @PostMapping("/carrito-items")
    public ResponseEntity<CarritoItemResponseDTO> crearItem(
            @Valid @RequestBody CarritoItemRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.crearItem(request));
    }

    @PutMapping("/carrito-items/{id}")
    public ResponseEntity<CarritoItemResponseDTO> actualizarItem(
            @PathVariable Long id,
            @Valid @RequestBody CarritoItemRequestDTO request
    ) {
        return ResponseEntity.ok(carritoService.actualizarItem(id, request));
    }

    @DeleteMapping("/carrito-items/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        carritoService.eliminarItem(id);
        return ResponseEntity.noContent().build();
    }
}
