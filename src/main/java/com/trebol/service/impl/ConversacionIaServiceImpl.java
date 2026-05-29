package com.trebol.service.impl;

import com.trebol.dto.ConversacionIaRequestDTO;
import com.trebol.dto.ConversacionIaResponseDTO;
import com.trebol.entity.ConversacionIa;
import com.trebol.repository.ConversacionIaRepository;
import com.trebol.service.ConversacionIaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversacionIaServiceImpl implements ConversacionIaService {

    private final ConversacionIaRepository conversacionIaRepository;

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
        ConversacionIa conversacion = ConversacionIa.builder()
                .usuarioId(request.getUsuarioId())
                .mensajeUsuario(request.getMensajeUsuario())
                .mensajeIa(request.getMensajeIa())
                .contexto(request.getContexto())
                .modelo(request.getModelo())
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();

        return mapear(conversacionIaRepository.save(conversacion));
    }

    @Override
    public ConversacionIaResponseDTO actualizar(Long id, ConversacionIaRequestDTO request) {
        ConversacionIa conversacion = conversacionIaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conversación IA no encontrada"));

        conversacion.setUsuarioId(request.getUsuarioId());
        conversacion.setMensajeUsuario(request.getMensajeUsuario());
        conversacion.setMensajeIa(request.getMensajeIa());
        conversacion.setContexto(request.getContexto());
        conversacion.setModelo(request.getModelo());
        conversacion.setFechaActualizacion(LocalDateTime.now());

        return mapear(conversacionIaRepository.save(conversacion));
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
