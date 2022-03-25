package com.example.apelsinrestapi.service;

import com.example.apelsinrestapi.dto.ApiResponse;
import com.example.apelsinrestapi.dto.BulkProduct;
import com.example.apelsinrestapi.dto.ProductDto;
import com.example.apelsinrestapi.dto.HighDemandProduct;
import com.example.apelsinrestapi.entity.Category;
import com.example.apelsinrestapi.entity.Detail;
import com.example.apelsinrestapi.entity.Product;
import com.example.apelsinrestapi.repository.CategoryRepository;
import com.example.apelsinrestapi.repository.DetailRepository;
import com.example.apelsinrestapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public record ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, DetailRepository detailRepository) {
    public ApiResponse add(ProductDto productDto) {
        Optional<Category> byId = categoryRepository.findById(productDto.getCategoryId());
        if (byId.isEmpty()) {
            return new ApiResponse("NOT FOUND", false);
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setPhoto(productDto.getPhoto());
        product.setCategory(byId.get());
        productRepository.save(product);

        return new ApiResponse("ADDED", true);
    }

    public ApiResponse edit(Integer id, ProductDto productDto) {
        Optional<Product> byId = productRepository.findById(id);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (byId.isEmpty() || optionalCategory.isEmpty()) {
            return new ApiResponse("NOT FOUND", false);
        }
        Product product = byId.get();
        product.setCategory(optionalCategory.get());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setPhoto(productDto.getPhoto());

        Product edit = productRepository.save(product);
        return new ApiResponse("EDITED", true, edit);
    }
    public ApiResponse getHighDemandProducts() {
        List<Product> productsQuantity = productRepository.getProductsQuantity();
        List<HighDemandProduct> products = productsQuantity.stream().map(this::productToHighDemandProduct).toList();
        return new ApiResponse("Mana", true, products);
    }

    public HighDemandProduct productToHighDemandProduct(Product product) {
        List<Detail> detailList = detailRepository.findAllByProduct_Id(product.getId());
        HighDemandProduct highDemandProduct = new HighDemandProduct();
        for (Detail detail : detailList) {
            highDemandProduct.setName(product.getName());
            highDemandProduct.setId(product.getId());
            highDemandProduct.setOrderQuantity(detail.getQuantity());
        }
        return highDemandProduct;
    }


    public ApiResponse getBulkProducts() {
        List<Product> productList = productRepository.getProductsBulk();
        List<BulkProduct> collect = productList.stream().map(this::productToBulkProduct).toList();
        return new ApiResponse("Mana", true, collect);
    }

    public BulkProduct productToBulkProduct(Product product) {
        return new BulkProduct(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
