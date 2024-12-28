package br.com.eremate.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import br.com.eremate.ecommerce.exception.ResourceNotFoundException;
import br.com.eremate.ecommerce.model.*;
import br.com.eremate.ecommerce.repository.*;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, DeliveryRepository deliveryRepository, CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.deliveryRepository = deliveryRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }

    public Order criarPedidoAPartirDoCarrinho(Cart cart) {
        if (Boolean.TRUE.equals(cart.getAtivo())) {
            throw new IllegalStateException("Carrinho ainda está ativo, feche o carrinho antes de criar um pedido.");
        }

        // Calcula total
        List<CartItem> itens = cartItemRepository.findAll().stream().filter(i -> i.getCart().getId().equals(cart.getId())).toList();

        double total = itens.stream().mapToDouble(i -> i.getProduct().getPreco() * i.getQuantidade()).sum();

        Order order = Order.builder().user(cart.getUser()).dataCriacao(LocalDateTime.now()).status("PENDENTE").total(total).build();

        Order savedOrder = orderRepository.save(order);

        // Cria orderItems
        for (CartItem item : itens) {
            OrderItem orderItem = OrderItem.builder().order(savedOrder).product(item.getProduct()).quantidade(item.getQuantidade()).precoUnitario(item.getProduct().getPreco()).build();
            orderItemRepository.save(orderItem);
        }

        // Criar registro de entrega
        Delivery delivery = Delivery.builder().order(savedOrder).statusEntrega("AGUARDANDO_ENVIO").dataUltimaAtualizacao(LocalDateTime.now()).build();
        deliveryRepository.save(delivery);

        return savedOrder;
    }

    public Order buscarOrderPorId(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + orderId));
    }

    public List<Order> listarPedidos() {
        return orderRepository.findAll();
    }

    public void atualizarStatusPedido(Long orderId, String novoStatus) {
        Order order = buscarOrderPorId(orderId);
        order.setStatus(novoStatus);
        orderRepository.save(order);
    }

    public void atualizarStatusEntrega(Long orderId, String novoStatusEntrega) {
        // busca a entrega pelo orderId
        Delivery delivery = deliveryRepository.findAll().stream().filter(d -> d.getOrder().getId().equals(orderId)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrada para o pedido ID: " + orderId));

        delivery.setStatusEntrega(novoStatusEntrega);
        delivery.setDataUltimaAtualizacao(LocalDateTime.now());
        deliveryRepository.save(delivery);
    }
}
