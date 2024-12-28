package br.com.eremate.ecommerce.service;

import br.com.eremate.ecommerce.exception.ResourceNotFoundException;
import br.com.eremate.ecommerce.model.Product;
import br.com.eremate.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product salvarProduto(Product product) {
        return productRepository.save(product);
    }

    public List<Product> listarProdutos() {
        return productRepository.findAll();
    }

    public Product buscarPorId(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
    }

    public void deletarProduto(Long id) {
        productRepository.deleteById(id);
    }
}