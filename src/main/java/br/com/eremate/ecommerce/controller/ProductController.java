package br.com.eremate.ecommerce.controller;

import br.com.eremate.ecommerce.model.Product;
import br.com.eremate.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product criarProduto(@RequestBody Product product) {
        return productService.salvarProduto(product);
    }

    @GetMapping
    public List<Product> listarProdutos() {
        return productService.listarProdutos();
    }

    @GetMapping("/{id}")
    public Product buscarProduto(@PathVariable Long id) {
        return productService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        productService.deletarProduto(id);
    }
}
