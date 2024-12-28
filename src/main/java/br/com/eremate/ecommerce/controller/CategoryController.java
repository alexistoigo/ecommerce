package br.com.eremate.ecommerce.controller;

import br.com.eremate.ecommerce.model.Category;
import br.com.eremate.ecommerce.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category criarCategoria(@RequestBody Category category) {
        return categoryService.salvarCategoria(category);
    }

    @GetMapping
    public List<Category> listarCategorias() {
        return categoryService.listarCategorias();
    }

    @GetMapping("/{id}")
    public Category buscarCategoria(@PathVariable Long id) {
        return categoryService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarCategoria(@PathVariable Long id) {
        categoryService.deletarCategoria(id);
    }
}
