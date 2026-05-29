package com.trebol.service.impl;

import com.trebol.dto.PedidoRequestDTO;
import com.trebol.dto.PedidoResponseDTO;
import com.trebol.entity.Pedido;
import com.trebol.entity.Usuario;
import com.trebol.repository.PedidoRepository;
import com.trebol.repository.UsuarioRepository;
import com.trebol.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<PedidoResponseDTO> listar() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
        return mapear(pedido);
    }

    @Override
    public PedidoResponseDTO crear(PedidoRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .subtotal(request.getSubtotal())
                .envio(request.getEnvio())
                .total(request.getTotal())
                .direccionEnvio(request.getDireccionEnvio())
                .metodoPago(request.getMetodoPago())
                .estado(request.getEstado())
                .build();

        Pedido guardado = pedidoRepository.save(pedido);
        return mapear(guardado);
    }

    @Override
    public PedidoResponseDTO actualizar(Long id, PedidoRequestDTO request) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

        if (request.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            pedido.setUsuario(usuario);
        }
        pedido.setSubtotal(request.getSubtotal());
        pedido.setEnvio(request.getEnvio());
        pedido.setTotal(request.getTotal());
        pedido.setDireccionEnvio(request.getDireccionEnvio());
        pedido.setMetodoPago(request.getMetodoPago());
        pedido.setEstado(request.getEstado());

        Pedido actualizado = pedidoRepository.save(pedido);
        return mapear(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new IllegalArgumentException("Pedido no encontrado");
        }
        pedidoRepository.deleteById(id);
    }

    private PedidoResponseDTO mapear(Pedido pedido) {
        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .usuarioId(pedido.getUsuario() != null ? pedido.getUsuario().getId() : null)
                .subtotal(pedido.getSubtotal())
                .envio(pedido.getEnvio())
                .total(pedido.getTotal())
                .direccionEnvio(pedido.getDireccionEnvio())
                .metodoPago(pedido.getMetodoPago())
                .estado(pedido.getEstado())
                .fecha(pedido.getFecha())
                .build();
    }
}
