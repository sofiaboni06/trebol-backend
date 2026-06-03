package com.trebol.service.impl;

import com.trebol.dto.CategoriaResponseDTO;
import com.trebol.dto.ProductoRequestDTO;
import com.trebol.dto.ProductoResponseDTO;
import com.trebol.entity.Categoria;
import com.trebol.entity.Producto;
import com.trebol.exception.ResourceNotFoundException;
import com.trebol.repository.CategoriaRepository;
import com.trebol.repository.ProductoRepository;
import com.trebol.service.ProductoService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public ProductoResponseDTO crear(ProductoRequestDTO request) {

        Categoria categoria = null;
        if (request.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(request.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));
        }

        Producto producto = Producto.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .sku(request.getSku())
                .precio(request.getPrecio())
                .stock(request.getStock())
                .imagenPrincipal(request.getImagenPrincipal())
                .categoria(categoria)
                .tipoProducto(request.getTipoProducto())
                .requiereCuidados(request.getRequiereCuidados())
                .estado(request.getEstado())
                .slug(request.getSlug())
                .build();

        producto = productoRepository.save(producto);

        return mapear(producto);
    }

    @Override
    public List<ProductoResponseDTO> listar() {

        return productoRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    public List<ProductoResponseDTO> buscar(
            String nombre,
            Long categoriaId,
            BigDecimal precioMin,
            BigDecimal precioMax
    ) {
        return productoRepository.findAll()
                .stream()
                .filter(producto -> {
                    // Filtrar por nombre
                    if (nombre != null && !nombre.isBlank()) {
                        String nombreLower = nombre.toLowerCase();
                        if (!producto.getNombre().toLowerCase().contains(nombreLower) &&
                            !producto.getDescripcion().toLowerCase().contains(nombreLower)) {
                            return false;
                        }
                    }

                    // Filtrar por categoría
                    if (categoriaId != null) {
                        if (producto.getCategoria() == null || 
                            !producto.getCategoria().getId().equals(categoriaId)) {
                            return false;
                        }
                    }

                    // Filtrar por rango de precio
                    if (precioMin != null && producto.getPrecio().compareTo(precioMin) < 0) {
                        return false;
                    }
                    if (precioMax != null && producto.getPrecio().compareTo(precioMax) > 0) {
                        return false;
                    }

                    return true;
                })
                .map(this::mapear)
                .toList();
    }

    @Override
    public ProductoResponseDTO buscarPorId(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        return mapear(producto);
    }

    @Override
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO request) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        if (request.getNombre() != null) {
            producto.setNombre(request.getNombre());
        }
        if (request.getDescripcion() != null) {
            producto.setDescripcion(request.getDescripcion());
        }
        if (request.getSku() != null) {
            producto.setSku(request.getSku());
        }
        if (request.getPrecio() != null) {
            producto.setPrecio(request.getPrecio());
        }
        if (request.getStock() != null) {
            producto.setStock(request.getStock());
        }
        if (request.getImagenPrincipal() != null) {
            producto.setImagenPrincipal(request.getImagenPrincipal());
        }
        if (request.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));
            producto.setCategoria(categoria);
        }
        if (request.getTipoProducto() != null) {
            producto.setTipoProducto(request.getTipoProducto());
        }
        if (request.getRequiereCuidados() != null) {
            producto.setRequiereCuidados(request.getRequiereCuidados());
        }
        if (request.getEstado() != null) {
            producto.setEstado(request.getEstado());
        }
        if (request.getSlug() != null) {
            producto.setSlug(request.getSlug());
        }

        producto = productoRepository.save(producto);

        return mapear(producto);
    }

    @Override
    public void eliminar(Long id) {

        productoRepository.deleteById(id);
    }

    private ProductoResponseDTO mapear(Producto producto) {

        ProductoResponseDTO.ProductoResponseDTOBuilder builder = ProductoResponseDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .sku(producto.getSku())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .imagenPrincipal(producto.getImagenPrincipal())
                .tipoProducto(producto.getTipoProducto())
                .requiereCuidados(producto.getRequiereCuidados())
                .estado(producto.getEstado())
                .slug(producto.getSlug())
                .fechaCreacion(producto.getFechaCreacion());

        if (producto.getCategoria() != null) {
            CategoriaResponseDTO categoriaDTODto = CategoriaResponseDTO.builder()
                    .id(producto.getCategoria().getId())
                    .nombre(producto.getCategoria().getNombre())
                    .descripcion(producto.getCategoria().getDescripcion())
                    .imagen(producto.getCategoria().getImagen())
                    .build();
            builder.categoria(categoriaDTODto);
        }

        return builder.build();
    }
}
