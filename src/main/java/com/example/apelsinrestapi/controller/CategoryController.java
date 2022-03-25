package com.example.apelsinrestapi.controller;

import com.example.apelsinrestapi.dto.ApiResponse;
import com.example.apelsinrestapi.entity.Category;
import com.example.apelsinrestapi.entity.Product;
import com.example.apelsinrestapi.repository.CategoryRepository;
import com.example.apelsinrestapi.repository.ProductRepository;
import com.example.apelsinrestapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    final CategoryRepository categoryRepository;
    final CategoryService categoryService;
    final ProductRepository productRepository;

    @GetMapping
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(categoryRepository.findAllByActiveTrue());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return ResponseEntity.status(optionalCategory.isEmpty() ? 404 : 200).body(optionalCategory.orElse(new Category()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody Category category) {
        Category save = categoryRepository.save(category);
        return ResponseEntity.ok().body(save);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody Category category) {
        ApiResponse edit = categoryService.edit(id, category);
        return ResponseEntity.status(edit.isSuccess() ? 200 : 409).body(edit);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Category category = byId.get();
        category.setActive(false);
        categoryRepository.save(category);
        return ResponseEntity.ok().body("DELETED");
    }

    @GetMapping("/list")
    public HttpEntity<?> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAllByActiveTrue();
        ApiResponse response = new ApiResponse("Mana", true, categoryList);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/")
    public HttpEntity<?> getCategoryByProductId(@RequestParam Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Product product = optionalProduct.get();
        Category category = product.getCategory();
        ApiResponse response = new ApiResponse("Mana", true, category);
        return ResponseEntity.ok().body(response);
    }
}
