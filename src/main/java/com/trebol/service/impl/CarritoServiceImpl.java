package com.trebol.service.impl;

import com.trebol.dto.CarritoItemRequestDTO;
import com.trebol.dto.CarritoItemResponseDTO;
import com.trebol.dto.CarritoRequestDTO;
import com.trebol.dto.CarritoResponseDTO;
import com.trebol.entity.Carrito;
import com.trebol.entity.CarritoItem;
import com.trebol.entity.Producto;
import com.trebol.entity.Usuario;
import com.trebol.repository.CarritoItemRepository;
import com.trebol.repository.CarritoRepository;
import com.trebol.repository.ProductoRepository;
import com.trebol.repository.UsuarioRepository;
import com.trebol.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public List<CarritoResponseDTO> listarCarritos() {
        return carritoRepository.findAll()
                .stream()
                .map(this::mapearCarrito)
                .toList();
    }

    @Override
    public CarritoResponseDTO buscarCarritoPorId(Long id) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));
        return mapearCarrito(carrito);
    }

    @Override
    public CarritoResponseDTO crearCarrito(CarritoRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Carrito carrito = Carrito.builder()
                .usuario(usuario)
                .estado(request.getEstado())
                .build();

        return mapearCarrito(carritoRepository.save(carrito));
    }

    @Override
    public CarritoResponseDTO actualizarCarrito(Long id, CarritoRequestDTO request) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        carrito.setUsuario(usuario);
        carrito.setEstado(request.getEstado());

        return mapearCarrito(carritoRepository.save(carrito));
    }

    @Override
    public void eliminarCarrito(Long id) {
        if (!carritoRepository.existsById(id)) {
            throw new IllegalArgumentException("Carrito no encontrado");
        }
        carritoRepository.deleteById(id);
    }

    @Override
    public List<CarritoItemResponseDTO> listarItems() {
        return carritoItemRepository.findAll()
                .stream()
                .map(this::mapearCarritoItem)
                .toList();
    }

    @Override
    public CarritoItemResponseDTO buscarItemPorId(Long id) {
        CarritoItem item = carritoItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item de carrito no encontrado"));
        return mapearCarritoItem(item);
    }

    @Override
    public CarritoItemResponseDTO crearItem(CarritoItemRequestDTO request) {
        Carrito carrito = carritoRepository.findById(request.getCarritoId())
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        CarritoItem item = CarritoItem.builder()
                .carrito(carrito)
                .producto(producto)
                .cantidad(request.getCantidad())
                .precio(request.getPrecio())
                .build();

        return mapearCarritoItem(carritoItemRepository.save(item));
    }

    @Override
    public CarritoItemResponseDTO actualizarItem(Long id, CarritoItemRequestDTO request) {
        CarritoItem item = carritoItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item de carrito no encontrado"));

        Carrito carrito = carritoRepository.findById(request.getCarritoId())
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        item.setCarrito(carrito);
        item.setProducto(producto);
        item.setCantidad(request.getCantidad());
        item.setPrecio(request.getPrecio());

        return mapearCarritoItem(carritoItemRepository.save(item));
    }

    @Override
    public void eliminarItem(Long id) {
        if (!carritoItemRepository.existsById(id)) {
            throw new IllegalArgumentException("Item de carrito no encontrado");
        }
        carritoItemRepository.deleteById(id);
    }

    private CarritoResponseDTO mapearCarrito(Carrito carrito) {
        return CarritoResponseDTO.builder()
                .id(carrito.getId())
                .usuarioId(carrito.getUsuario() != null ? carrito.getUsuario().getId() : null)
                .estado(carrito.getEstado())
                .fechaCreacion(carrito.getFechaCreacion())
                .items(carrito.getItems() == null ? null : carrito.getItems()
                        .stream()
                        .map(this::mapearCarritoItem)
                        .toList())
                .build();
    }

    private CarritoItemResponseDTO mapearCarritoItem(CarritoItem item) {
        return CarritoItemResponseDTO.builder()
                .id(item.getId())
                .carritoId(item.getCarrito() != null ? item.getCarrito().getId() : null)
                .productoId(item.getProducto() != null ? item.getProducto().getId() : null)
                .cantidad(item.getCantidad())
                .precio(item.getPrecio())
                .build();
    }
}
