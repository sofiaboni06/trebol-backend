package com.trebol.controller;

import com.trebol.dto.ServicioRequestDTO;
import com.trebol.dto.ServicioResponseDTO;
import com.trebol.service.ServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<ServicioResponseDTO>> listar() {
        return ResponseEntity.ok(servicioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ServicioResponseDTO> crear(@Valid @RequestBody ServicioRequestDTO request) {
        ServicioResponseDTO response = servicioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ServicioRequestDTO request
    ) {
        return ResponseEntity.ok(servicioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
