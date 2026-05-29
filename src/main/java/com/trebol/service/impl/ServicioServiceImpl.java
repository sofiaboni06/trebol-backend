package com.trebol.service.impl;

import com.trebol.dto.ServicioRequestDTO;
import com.trebol.dto.ServicioResponseDTO;
import com.trebol.entity.Servicio;
import com.trebol.repository.ServicioRepository;
import com.trebol.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;

    @Override
    public List<ServicioResponseDTO> listar() {
        return servicioRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    public ServicioResponseDTO buscarPorId(Long id) {
        Optional<Servicio> optional = servicioRepository.findById(id);
        Servicio servicio = optional.orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
        return mapear(servicio);
    }

    @Override
    public ServicioResponseDTO crear(ServicioRequestDTO request) {
        Servicio servicio = Servicio.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .precioBase(request.getPrecioBase())
                .imagen(request.getImagen())
                .estado(request.getEstado())
                .build();

        Servicio guardado = servicioRepository.save(servicio);
        return mapear(guardado);
    }

    @Override
    public ServicioResponseDTO actualizar(Long id, ServicioRequestDTO request) {
        Optional<Servicio> optional = servicioRepository.findById(id);
        Servicio servicio = optional.orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

        servicio.setNombre(request.getNombre());
        servicio.setDescripcion(request.getDescripcion());
        servicio.setPrecioBase(request.getPrecioBase());
        servicio.setImagen(request.getImagen());
        servicio.setEstado(request.getEstado());

        Servicio actualizado = servicioRepository.save(servicio);
        return mapear(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new IllegalArgumentException("Servicio no encontrado");
        }
        servicioRepository.deleteById(id);
    }

    private ServicioResponseDTO mapear(Servicio servicio) {
        return ServicioResponseDTO.builder()
                .id(servicio.getId())
                .nombre(servicio.getNombre())
                .descripcion(servicio.getDescripcion())
                .precioBase(servicio.getPrecioBase())
                .imagen(servicio.getImagen())
                .estado(servicio.getEstado())
                .build();
    }
}
