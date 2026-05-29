package com.trebol.dto;

import com.trebol.entity.Producto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponseDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private String sku;

    private BigDecimal precio;

    private Integer stock;

    private String imagenPrincipal;

    private CategoriaResponseDTO categoria;

    private Producto.TipoProducto tipoProducto;

    private Boolean requiereCuidados;

    private Boolean estado;

    private String slug;

    private LocalDateTime fechaCreacion;
}
