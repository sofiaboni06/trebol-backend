package com.trebol.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private String referenciaPago;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoPago estado = EstadoPago.PENDIENTE;

    @Builder.Default
    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago = LocalDateTime.now();

    public enum MetodoPago {
        NEQUI, DAVIPLATA, TARJETA, PSE, EFECTIVO
    }

    public enum EstadoPago {
        PENDIENTE, APROBADO, RECHAZADO, REEMBOLSADO
    }
}
