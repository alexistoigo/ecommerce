package br.com.eremate.ecommerce.service;

import br.com.eremate.ecommerce.exception.ResourceNotFoundException;
import br.com.eremate.ecommerce.model.Category;
import br.com.eremate.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category salvarCategoria(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> listarCategorias() {
        return categoryRepository.findAll();
    }

    public Category buscarPorId(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
    }

    public void deletarCategoria(Long id) {
        categoryRepository.deleteById(id);
    }
}
