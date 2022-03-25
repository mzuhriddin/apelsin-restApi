package com.example.apelsinrestapi.controller;

import com.example.apelsinrestapi.dto.ApiResponse;
import com.example.apelsinrestapi.dto.ProductDto;
import com.example.apelsinrestapi.entity.Product;
import com.example.apelsinrestapi.repository.ProductRepository;
import com.example.apelsinrestapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    final ProductRepository productRepository;
    final ProductService productService;

    @GetMapping
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(productRepository.findAllByActiveTrue());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return ResponseEntity.status(optionalProduct.isEmpty() ? 404 : 200).body(optionalProduct.orElse(new Product()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody ProductDto productDto) {
        ApiResponse apiResponse = productService.add(productDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody ProductDto productDto) {
        ApiResponse edit = productService.edit(id, productDto);
        return ResponseEntity.status(edit.isSuccess() ? 200 : 409).body(edit);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Product product = byId.get();
        product.setActive(false);
        productRepository.save(product);
        return ResponseEntity.ok().body("DELETED");
    }

    @GetMapping("/high_demand_products")
    public HttpEntity<?> getHighDemandProducts() {
        ApiResponse response = productService.getHighDemandProducts();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/bulk_products")
    public HttpEntity<?> getBulkProducts() {
        ApiResponse response = productService.getBulkProducts();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list")
    public HttpEntity<?> getProductList() {
        List<Product> productList = productRepository.findAllByActiveTrue();
        ApiResponse response = new ApiResponse("Mana", true, productList);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/details")
    public HttpEntity<?> getDetailsByProductId(@RequestParam Integer productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Product product = productOptional.get();
        ApiResponse response = new ApiResponse("Mana", true, product);
        return ResponseEntity.ok().body(response);
    }
}
