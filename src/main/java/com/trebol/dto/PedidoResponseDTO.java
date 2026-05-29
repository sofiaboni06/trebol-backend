package com.trebol.dto;

import com.trebol.entity.Pedido;
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
public class PedidoResponseDTO {

    private Long id;
    private Long usuarioId;
    private BigDecimal subtotal;
    private BigDecimal envio;
    private BigDecimal total;
    private String direccionEnvio;
    private String metodoPago;
    private Pedido.EstadoPedido estado;
    private LocalDateTime fecha;
}
