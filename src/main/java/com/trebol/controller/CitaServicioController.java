package com.trebol.controller;

import com.trebol.dto.CitaServicioRequestDTO;
import com.trebol.dto.CitaServicioResponseDTO;
import com.trebol.service.CitaServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaServicioController {

    private final CitaServicioService citaServicioService;

    @GetMapping
    public ResponseEntity<List<CitaServicioResponseDTO>> listar() {
        return ResponseEntity.ok(citaServicioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaServicioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaServicioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CitaServicioResponseDTO> crear(@Valid @RequestBody CitaServicioRequestDTO request) {
        CitaServicioResponseDTO response = citaServicioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaServicioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CitaServicioRequestDTO request
    ) {
        return ResponseEntity.ok(citaServicioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaServicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
