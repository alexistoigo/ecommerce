package br.com.eremate.ecommerce.service;

import br.com.eremate.ecommerce.exception.ResourceNotFoundException;
import br.com.eremate.ecommerce.model.Cart;
import br.com.eremate.ecommerce.model.CartItem;
import br.com.eremate.ecommerce.model.Product;
import br.com.eremate.ecommerce.model.User;
import br.com.eremate.ecommerce.repository.CartItemRepository;
import br.com.eremate.ecommerce.repository.CartRepository;
import br.com.eremate.ecommerce.repository.ProductRepository;
import br.com.eremate.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Cart criarCarrinho(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + userId));

        // Verifica se já existe um carrinho ativo para este usuário
        List<Cart> carrinhosDoUsuario = cartRepository.findAll();
        for (Cart c : carrinhosDoUsuario) {
            if (c.getUser().getId().equals(userId) && Boolean.TRUE.equals(c.getAtivo())) {
                return c;
            }
        }

        Cart cart = Cart.builder().user(user).ativo(true).build();

        return cartRepository.save(cart);
    }

    public CartItem adicionarItemNoCarrinho(Long cartId, Long productId, int quantidade) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com ID: " + cartId));

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + productId));

        // fazer validação de estoque, etc.

        CartItem cartItem = CartItem.builder().cart(cart).product(product).quantidade(quantidade).build();

        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> listarItensDoCarrinho(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com ID: " + cartId));
        // filtrar cartItems por cartId...
        return cartItemRepository.findAll().stream().filter(i -> i.getCart().getId().equals(cartId)).toList();
    }

    public void removerItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public Cart fecharCarrinho(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com ID: " + cartId));
        cart.setAtivo(false);
        return cartRepository.save(cart);
    }
}