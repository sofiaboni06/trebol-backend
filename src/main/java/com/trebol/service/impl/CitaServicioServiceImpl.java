package com.trebol.service.impl;

import com.trebol.dto.CitaServicioRequestDTO;
import com.trebol.dto.CitaServicioResponseDTO;
import com.trebol.entity.CitaServicio;
import com.trebol.entity.Servicio;
import com.trebol.entity.Usuario;
import com.trebol.repository.CitaServicioRepository;
import com.trebol.repository.ServicioRepository;
import com.trebol.repository.UsuarioRepository;
import com.trebol.service.CitaServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CitaServicioServiceImpl implements CitaServicioService {

    private final CitaServicioRepository citaServicioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicioRepository servicioRepository;

    @Override
    public List<CitaServicioResponseDTO> listar() {
        return citaServicioRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    public CitaServicioResponseDTO buscarPorId(Long id) {
        Optional<CitaServicio> optional = citaServicioRepository.findById(id);
        CitaServicio cita = optional.orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
        return mapear(cita);
    }

    @Override
    public CitaServicioResponseDTO crear(CitaServicioRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Servicio servicio = servicioRepository.findById(request.getServicioId())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

        CitaServicio cita = CitaServicio.builder()
                .usuario(usuario)
                .servicio(servicio)
                .fecha(request.getFecha())
                .hora(request.getHora())
                .direccion(request.getDireccion())
                .descripcionCliente(request.getDescripcionCliente())
                .estado(request.getEstado())
                .build();

        return mapear(citaServicioRepository.save(cita));
    }

    @Override
    public CitaServicioResponseDTO actualizar(Long id, CitaServicioRequestDTO request) {
        CitaServicio cita = citaServicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Servicio servicio = servicioRepository.findById(request.getServicioId())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

        cita.setUsuario(usuario);
        cita.setServicio(servicio);
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setDireccion(request.getDireccion());
        cita.setDescripcionCliente(request.getDescripcionCliente());
        cita.setEstado(request.getEstado());

        return mapear(citaServicioRepository.save(cita));
    }

    @Override
    public void eliminar(Long id) {
        if (!citaServicioRepository.existsById(id)) {
            throw new IllegalArgumentException("Cita no encontrada");
        }
        citaServicioRepository.deleteById(id);
    }

    private CitaServicioResponseDTO mapear(CitaServicio cita) {
        return CitaServicioResponseDTO.builder()
                .id(cita.getId())
                .usuarioId(cita.getUsuario() != null ? cita.getUsuario().getId() : null)
                .servicioId(cita.getServicio() != null ? cita.getServicio().getId() : null)
                .fecha(cita.getFecha())
                .hora(cita.getHora())
                .direccion(cita.getDireccion())
                .descripcionCliente(cita.getDescripcionCliente())
                .estado(cita.getEstado())
                .build();
    }
}
