package br.com.eremate.ecommerce.controller;

import br.com.eremate.ecommerce.model.Cart;
import br.com.eremate.ecommerce.model.CartItem;
import br.com.eremate.ecommerce.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Cria um carrinho para um usuário
    @PostMapping("/create/{userId}")
    public Cart criarCarrinho(@PathVariable Long userId) {
        return cartService.criarCarrinho(userId);
    }

    // Adicionar item no carrinho
    @PostMapping("/{cartId}/add-item/{productId}")
    public CartItem adicionarItem(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantidade) {
        return cartService.adicionarItemNoCarrinho(cartId, productId, quantidade);
    }

    // Listar itens do carrinho
    @GetMapping("/{cartId}/items")
    public List<CartItem> listarItens(@PathVariable Long cartId) {
        return cartService.listarItensDoCarrinho(cartId);
    }

    // Remover item do carrinho
    @DeleteMapping("/remove-item/{cartItemId}")
    public void removerItem(@PathVariable Long cartItemId) {
        cartService.removerItem(cartItemId);
    }

    // Fechar carrinho
    @PutMapping("/{cartId}/fechar")
    public Cart fecharCarrinho(@PathVariable Long cartId) {
        return cartService.fecharCarrinho(cartId);
    }
}