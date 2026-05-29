package com.trebol.dto;

import com.trebol.entity.Pedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoRequestDTO {

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El subtotal es obligatorio")
    private BigDecimal subtotal;

    @NotNull(message = "El envío es obligatorio")
    private BigDecimal envio;

    @NotNull(message = "El total es obligatorio")
    private BigDecimal total;

    @NotBlank(message = "La dirección de envío es obligatoria")
    private String direccionEnvio;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    @NotNull(message = "El estado del pedido es obligatorio")
    private Pedido.EstadoPedido estado;
}
