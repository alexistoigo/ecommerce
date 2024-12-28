package br.com.eremate.ecommerce.controller;

import br.com.eremate.ecommerce.model.Cart;
import br.com.eremate.ecommerce.model.Order;
import br.com.eremate.ecommerce.service.CartService;
import br.com.eremate.ecommerce.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    // Cria pedido a partir de um carrinho (que já deve estar fechado)
    @PostMapping("/create-from-cart/{cartId}")
    public Order criarPedidoAPartirDoCarrinho(@PathVariable Long cartId) {
        Cart cart = cartService.fecharCarrinho(cartId);
        // Se não estiver fechado ainda, a Service vai fechar.
        return orderService.criarPedidoAPartirDoCarrinho(cart);
    }

    @GetMapping
    public List<Order> listarPedidos() {
        return orderService.listarPedidos();
    }

    @GetMapping("/{orderId}")
    public Order buscarPedido(@PathVariable Long orderId) {
        return orderService.buscarOrderPorId(orderId);
    }

    // Atualizar status do pedido (ex: PENDENTE -> PAGO, PAGO -> ENVIADO, etc.)
    @PutMapping("/{orderId}/status")
    public void atualizarStatusPedido(@PathVariable Long orderId, @RequestParam String status) {
        orderService.atualizarStatusPedido(orderId, status);
    }

    // Atualizar status da entrega
    @PutMapping("/{orderId}/delivery-status")
    public void atualizarStatusEntrega(@PathVariable Long orderId, @RequestParam String statusEntrega) {
        orderService.atualizarStatusEntrega(orderId, statusEntrega);
    }
}
