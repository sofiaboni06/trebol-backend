package com.trebol.controller;

import com.trebol.dto.EscaneoPlantaRequestDTO;
import com.trebol.dto.EscaneoPlantaResponseDTO;
import com.trebol.service.EscaneoPlantaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escaneos")
@RequiredArgsConstructor
public class EscaneoPlantaController {

    private final EscaneoPlantaService escaneoPlantaService;

    @GetMapping
    public ResponseEntity<List<EscaneoPlantaResponseDTO>> listar() {
        return ResponseEntity.ok(escaneoPlantaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscaneoPlantaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(escaneoPlantaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EscaneoPlantaResponseDTO> crear(@Valid @RequestBody EscaneoPlantaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(escaneoPlantaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EscaneoPlantaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EscaneoPlantaRequestDTO request
    ) {
        return ResponseEntity.ok(escaneoPlantaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        escaneoPlantaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
