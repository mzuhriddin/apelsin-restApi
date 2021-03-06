package com.example.apelsinrestapi.repository;

import com.example.apelsinrestapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByActiveTrue();

    @Query(value = "select * from product p join details d on p.id = d.product_id where d.quantity >= 10", nativeQuery = true)
    List<Product> getProductsQuantity();

    @Query(value = "select * from product p join details d on p.id = d.product_id where d.quantity >= 8",nativeQuery = true)
    List<Product> getProductsBulk();
}
