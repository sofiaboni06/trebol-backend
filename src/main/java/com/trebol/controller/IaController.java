package com.trebol.controller;

import com.trebol.dto.IaRequestDTO;
import com.trebol.dto.IaResponseDTO;
import com.trebol.service.IaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for IA module endpoints.
 * 
 * All classification and response logic is delegated to IaService
 * to maintain separation of concerns and avoid code duplication.
 */
@RestController
@RequestMapping("/api/ia")
public class IaController {

    private final IaService iaService;

    @Autowired
    public IaController(IaService iaService) {
        this.iaService = iaService;
    }

    /**
     * Endpoint for asking questions to the IA classifier.
     * Classifies the question and generates an appropriate response.
     * 
     * @param request DTO containing pregunta (question) and tema (topic)
     * @return IaResponseDTO with classification result and response message
     */
    @PostMapping("/preguntar")
    public ResponseEntity<IaResponseDTO> preguntar(@RequestBody IaRequestDTO request) {
        IaResponseDTO response = iaService.preguntar(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for evaluating a consultation.
     * Uses the same classification logic as preguntar endpoint.
     * 
     * @param request DTO containing pregunta (question) and tema (topic)
     * @return IaResponseDTO with evaluation result
     */
    @PostMapping("/evaluar-consulta")
    public ResponseEntity<?> evaluarConsulta(@RequestBody IaRequestDTO request) {
        IaResponseDTO response = iaService.evaluarConsulta(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for listing allowed topics.
     * 
     * @return List of allowed topics for queries
     */
    @GetMapping("/temas-permitidos")
    public ResponseEntity<List<String>> temasPermitidos() {
        return ResponseEntity.ok(iaService.temasPermitidos());
    }
}
