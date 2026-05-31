package com.trebol.service.impl;

import com.trebol.dto.IaRequestDTO;
import com.trebol.dto.IaResponseDTO;
import com.trebol.service.IaService;
import com.trebol.service.ConsultaCategoria;
import com.trebol.service.ConsultaClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class IaServiceImpl implements IaService {

    // Response type constants
    private static final String RIESGO_ALTO = "RIESGO_ALTO";
    private static final String REDIRECCION_CITA = "REDIRECCION_CITA";
    private static final String RESPUESTA = "RESPUESTA";
    private static final String EVALUACION_OK = "EVALUACION_OK";

    // Response message constants
    private static final String MSG_RIESGO_ALTO =
            "Tu consulta requiere evaluación profesional para garantizar un diagnóstico seguro y preciso.";
    private static final String MSG_REDIRECCION_CITA =
            "Esta consulta requiere atención personalizada. Por favor agenda una cita con uno de nuestros especialistas.";
    
    // Redirect URL constant
    private static final String REDIRECT_CITAS = "/calendario-citas";

    private static final List<String> PERMITIDOS = Arrays.asList(
            "plantas",
            "cuidados",
            "riego",
            "fertilizacion",
            "plagas",
            "servicio",
            "producto"
    );

    private final ConsultaClassifierService classifierService;

    @Autowired
    public IaServiceImpl(ConsultaClassifierService classifierService) {
        this.classifierService = classifierService;
    }

    @Override
    public IaResponseDTO preguntar(IaRequestDTO request) {
        return procesar(request);
    }

    @Override
    public IaResponseDTO evaluarConsulta(IaRequestDTO request) {
        String tema = (request.getTema() == null ? "" : request.getTema()).toLowerCase();
        String pregunta = (request.getPregunta() == null ? "" : request.getPregunta()).toLowerCase();

        ConsultaCategoria categoria = classifierService.clasificar(pregunta, tema);
        if (categoria == ConsultaCategoria.RIESGO_ALTO) {
            return buildResponse(RIESGO_ALTO, MSG_RIESGO_ALTO, REDIRECT_CITAS, true);
        }

        if (categoria == ConsultaCategoria.REDIRECCION_CITA) {
            return buildResponse(REDIRECCION_CITA, MSG_REDIRECCION_CITA, REDIRECT_CITAS, true);
        }

        return buildResponse(EVALUACION_OK, "Consulta apta para respuesta automatizada.", null, false);
    }

    @Override
    public List<String> temasPermitidos() {
        return PERMITIDOS;
    }

    /**
     * Procesa una consulta y genera una respuesta según su categoría.
     * Centraliza toda la lógica de clasificación y respuesta.
     */
    private IaResponseDTO procesar(IaRequestDTO request) {
        String tema = request.getTema() != null ? request.getTema().toLowerCase() : "";
        String pregunta = request.getPregunta() != null ? request.getPregunta().trim() : "";

        ConsultaCategoria categoria = classifierService.clasificar(pregunta, tema);
        
        if (categoria == ConsultaCategoria.RIESGO_ALTO) {
            return buildResponse(RIESGO_ALTO, MSG_RIESGO_ALTO, REDIRECT_CITAS, true);
        }

        if (categoria == ConsultaCategoria.REDIRECCION_CITA) {
            return buildResponse(REDIRECCION_CITA, MSG_REDIRECCION_CITA, REDIRECT_CITAS, true);
        }

        // CONSULTA_PLANTA -> proceed to generate answer
        String respuesta = generarRespuestaCandidata(tema, pregunta);
        return buildResponse(RESPUESTA, respuesta, null, false);
    }

    /**
     * Factory method to build IaResponseDTO with consistent structure.
     */
    private IaResponseDTO buildResponse(String tipo, String mensaje, String redirectUrl, boolean requiereCita) {
        return new IaResponseDTO(tipo, mensaje, redirectUrl, requiereCita);
    }

    private String generarRespuestaCandidata(String tema, String pregunta) {
        if (!tema.isEmpty()) {
            if (PERMITIDOS.contains(tema)) {
                return "Puedo ayudarte con temas de '" + tema + "'. Consulta: " + pregunta + " — Aquí tienes una guía breve y sugerencias de manejo.";
            } else {
                return "Lo siento, ese tema no está cubierto por la respuesta automática. Por favor, solicita una cita si lo deseas.";
            }
        }
        return "Gracias por tu consulta. Proporciona el tema para obtener una respuesta más precisa.";
    }
}
