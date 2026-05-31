package com.trebol.controller;

import com.trebol.dto.ConversacionIaRequestDTO;
import com.trebol.dto.ConversacionIaResponseDTO;
import com.trebol.service.ConversacionIaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversaciones")
@RequiredArgsConstructor
public class ConversacionIaController {

    private final ConversacionIaService conversacionIaService;

    @GetMapping
    public ResponseEntity<List<ConversacionIaResponseDTO>> listar() {
        return ResponseEntity.ok(conversacionIaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversacionIaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(conversacionIaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ConversacionIaResponseDTO> crear(@Valid @RequestBody ConversacionIaRequestDTO request) {
        ConversacionIaResponseDTO response = conversacionIaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConversacionIaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConversacionIaRequestDTO request
    ) {
        return ResponseEntity.ok(conversacionIaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        conversacionIaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
