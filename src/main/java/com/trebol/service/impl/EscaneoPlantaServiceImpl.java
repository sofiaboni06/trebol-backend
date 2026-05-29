package com.trebol.service.impl;

import com.trebol.dto.EscaneoPlantaRequestDTO;
import com.trebol.dto.EscaneoPlantaResponseDTO;
import com.trebol.entity.EscaneoPlanta;
import com.trebol.repository.EscaneoPlantaRepository;
import com.trebol.service.EscaneoPlantaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EscaneoPlantaServiceImpl implements EscaneoPlantaService {

    private final EscaneoPlantaRepository escaneoPlantaRepository;

    @Override
    public List<EscaneoPlantaResponseDTO> listar() {
        return escaneoPlantaRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    public EscaneoPlantaResponseDTO buscarPorId(Long id) {
        EscaneoPlanta escaneo = escaneoPlantaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Escaneo de planta no encontrado"));
        return mapear(escaneo);
    }

    @Override
    public EscaneoPlantaResponseDTO crear(EscaneoPlantaRequestDTO request) {
        EscaneoPlanta escaneo = EscaneoPlanta.builder()
                .usuarioId(request.getUsuarioId())
                .nombrePlanta(request.getNombrePlanta())
                .descripcion(request.getDescripcion())
                .imagen(request.getImagen())
                .resultado(request.getResultado())
                .estado(request.getEstado())
                .build();

        return mapear(escaneoPlantaRepository.save(escaneo));
    }

    @Override
    public EscaneoPlantaResponseDTO actualizar(Long id, EscaneoPlantaRequestDTO request) {
        EscaneoPlanta escaneo = escaneoPlantaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Escaneo de planta no encontrado"));

        escaneo.setUsuarioId(request.getUsuarioId());
        escaneo.setNombrePlanta(request.getNombrePlanta());
        escaneo.setDescripcion(request.getDescripcion());
        escaneo.setImagen(request.getImagen());
        escaneo.setResultado(request.getResultado());
        escaneo.setEstado(request.getEstado());

        return mapear(escaneoPlantaRepository.save(escaneo));
    }

    @Override
    public void eliminar(Long id) {
        if (!escaneoPlantaRepository.existsById(id)) {
            throw new IllegalArgumentException("Escaneo de planta no encontrado");
        }
        escaneoPlantaRepository.deleteById(id);
    }

    private EscaneoPlantaResponseDTO mapear(EscaneoPlanta escaneo) {
        return EscaneoPlantaResponseDTO.builder()
                .id(escaneo.getId())
                .usuarioId(escaneo.getUsuarioId())
                .nombrePlanta(escaneo.getNombrePlanta())
                .descripcion(escaneo.getDescripcion())
                .imagen(escaneo.getImagen())
                .resultado(escaneo.getResultado())
                .estado(escaneo.getEstado())
                .fechaCreacion(escaneo.getFechaCreacion())
                .build();
    }
}
