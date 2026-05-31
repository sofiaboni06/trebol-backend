package com.trebol.service.impl;

import com.trebol.dto.ConversacionIaRequestDTO;
import com.trebol.dto.ConversacionIaResponseDTO;
import com.trebol.entity.ConversacionIa;
import com.trebol.repository.ConversacionIaRepository;
import com.trebol.service.ConversacionIaService;
import com.trebol.service.ConsultaCategoria;
import com.trebol.service.ConsultaClassifierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing IA conversations.
 * Uses the centralized ConsultaClassifierService for consistent classification
 * instead of implementing duplicate classification logic.
 */
@Service
@RequiredArgsConstructor
public class ConversacionIaServiceImpl implements ConversacionIaService {

    // Redirect URL constant - standardized across IA module
    private static final String REDIRECT_CITAS = "/calendario-citas";

    private final ConversacionIaRepository conversacionIaRepository;
    private final ConsultaClassifierService consultaClassifierService;

    @Override
    public List<ConversacionIaResponseDTO> listar() {
        return conversacionIaRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    public ConversacionIaResponseDTO buscarPorId(Long id) {
        ConversacionIa conversacion = conversacionIaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conversación IA no encontrada"));
        return mapear(conversacion);
    }

    @Override
    public ConversacionIaResponseDTO crear(ConversacionIaRequestDTO request) {
        String respuestaIa = request.getMensajeIa();
        if (respuestaIa == null || respuestaIa.isBlank()) {
            respuestaIa = generarRespuestaIa(request);
        }

        ConversacionIa conversacion = ConversacionIa.builder()
                .usuarioId(request.getUsuarioId())
                .mensajeUsuario(request.getMensajeUsuario())
                .mensajeIa(respuestaIa)
                .contexto(request.getContexto())
                .modelo(request.getModelo())
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();

        ConversacionIa saved = conversacionIaRepository.save(conversacion);
        ConversacionIaResponseDTO responseDto = mapear(saved);
        if (debeRedirigirAAgenda(request.getMensajeUsuario())) {
            responseDto.setRedirectUrl(REDIRECT_CITAS);
        }
        return responseDto;
    }

    @Override
    public ConversacionIaResponseDTO actualizar(Long id, ConversacionIaRequestDTO request) {
        ConversacionIa conversacion = conversacionIaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conversación IA no encontrada"));

        String respuestaIa = request.getMensajeIa();
        if (respuestaIa == null || respuestaIa.isBlank()) {
            respuestaIa = generarRespuestaIa(request);
        }

        conversacion.setUsuarioId(request.getUsuarioId());
        conversacion.setMensajeUsuario(request.getMensajeUsuario());
        conversacion.setMensajeIa(respuestaIa);
        conversacion.setContexto(request.getContexto());
        conversacion.setModelo(request.getModelo());
        conversacion.setFechaActualizacion(LocalDateTime.now());

        ConversacionIa saved = conversacionIaRepository.save(conversacion);
        ConversacionIaResponseDTO responseDto = mapear(saved);
        if (debeRedirigirAAgenda(request.getMensajeUsuario())) {
            responseDto.setRedirectUrl(REDIRECT_CITAS);
        }
        return responseDto;
    }

    /**
     * Generates a response for a user message.
     * Uses the centralized classifier to determine if the query requires appointment scheduling.
     */
    private String generarRespuestaIa(ConversacionIaRequestDTO request) {
        String mensajeUsuario = request.getMensajeUsuario() == null
                ? ""
                : request.getMensajeUsuario().trim().toLowerCase();

        if (mensajeUsuario.isBlank()) {
            return "Por favor describe tu pregunta sobre plantas para poder ayudarte.";
        }

        // Use the centralized classifier
        ConsultaCategoria categoria = consultaClassifierService.clasificar(mensajeUsuario, "");
        
        if (categoria == ConsultaCategoria.RIESGO_ALTO || categoria == ConsultaCategoria.REDIRECCION_CITA) {
            return "Esta consulta requiere atención personalizada. Por favor agenda una cita con uno de nuestros especialistas.";
        }

        // For CONSULTA_PLANTA, provide helpful responses
        if (contieneAlgunTermino(mensajeUsuario, "riego", "regar", "agua", "frecuencia")) {
            return "Para tus plantas, riega con moderación: más agua en verano y menos en invierno. Evita encharcar la tierra.";
        }

        if (contieneAlgunTermino(mensajeUsuario, "luz", "sol", "sombra", "iluminación")) {
            return "La mayoría de plantas de interior prefieren luz indirecta; las plantas de exterior necesitan sol parcial o pleno según la especie.";
        }

        if (contieneAlgunTermino(mensajeUsuario, "tierra", "sustrato", "abono", "fertilizante", "sustratos")) {
            return "Usa sustrato de buena calidad y fertiliza en temporada de crecimiento. Evita el exceso de fertilizante para no dañar las raíces.";
        }

        if (contieneAlgunTermino(mensajeUsuario, "plaga", "pulgón", "manchas", "hongos", "enfermedad")) {
            return "Para plagas o enfermedades, revisa hojas y raíces. Si necesitas una solución segura para tu planta, mejor agenda una cita con el encargado.";
        }

        if (contieneAlgunTermino(mensajeUsuario, "tipo de planta", "plantas", "paisajismo", "jardín", "paisaje")) {
            return "Te puedo ayudar con temas generales de plantas y paisajismo. Si quieres una asesoría personalizada, agenda tu cita aquí.";
        }

        return "Estas preguntas ya las responde el encargado de Trebol Paisajismo. Agenda tu cita para recibir asesoría personalizada.";
    }

    /**
     * Helper method to check if text contains any of the given terms.
     */
    private boolean contieneAlgunTermino(String texto, String... terminos) {
        for (String termino : terminos) {
            if (texto.contains(termino)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if a message requires appointment scheduling redirection.
     * Uses the centralized classifier to detect appointment-related queries.
     */
    private boolean debeRedirigirAAgenda(String mensaje) {
        if (mensaje == null) {
            return false;
        }
        String mensajeLower = mensaje.toLowerCase();
        ConsultaCategoria categoria = consultaClassifierService.clasificar(mensajeLower, "");
        return categoria == ConsultaCategoria.RIESGO_ALTO || categoria == ConsultaCategoria.REDIRECCION_CITA;
    }

    @Override
    public void eliminar(Long id) {
        if (!conversacionIaRepository.existsById(id)) {
            throw new IllegalArgumentException("Conversación IA no encontrada");
        }
        conversacionIaRepository.deleteById(id);
    }

    private ConversacionIaResponseDTO mapear(ConversacionIa conversacion) {
        return ConversacionIaResponseDTO.builder()
                .id(conversacion.getId())
                .usuarioId(conversacion.getUsuarioId())
                .mensajeUsuario(conversacion.getMensajeUsuario())
                .mensajeIa(conversacion.getMensajeIa())
                .contexto(conversacion.getContexto())
                .modelo(conversacion.getModelo())
                .fechaCreacion(conversacion.getFechaCreacion())
                .fechaActualizacion(conversacion.getFechaActualizacion())
                .build();
    }
}
