package com.trebol.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private BigDecimal subtotal;
    private BigDecimal envio;
    private BigDecimal total;

    private String direccionEnvio;
    private String metodoPago;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoPedido estado = EstadoPedido.PENDIENTE;

    @Builder.Default
    @Column(name = "fecha")
    private LocalDateTime fecha = LocalDateTime.now();

    public enum EstadoPedido {
        PENDIENTE, PAGADO, ENVIADO, ENTREGADO, CANCELADO
    }
}
