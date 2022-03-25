package com.example.apelsinrestapi.service;

import com.example.apelsinrestapi.dto.ApiResponse;
import com.example.apelsinrestapi.entity.Category;
import com.example.apelsinrestapi.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record CategoryService(CategoryRepository categoryRepository) {
    public ApiResponse edit(Integer id, Category category) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("NOT FOUND", false);
        }
        Category edit = byId.get();
        edit.setName(category.getName());

        return new ApiResponse("EDITED", true, categoryRepository.save(edit));
    }
}
