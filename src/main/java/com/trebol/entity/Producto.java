package com.trebol.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private String sku;

    private BigDecimal precio;

    @Builder.Default
    private Integer stock = 0;

    private String imagenPrincipal;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private TipoProducto tipoProducto;

    @Builder.Default
    private Boolean requiereCuidados = false;

    @Builder.Default
    private Boolean estado = true;

    @Builder.Default
    private String slug = "";

    @Builder.Default
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public enum TipoProducto {
        PLANTA, MACETA, FERTILIZANTE, FUNGICIDA, TIERRA, DECORACION, HERRAMIENTA
    }
}
