package com.example.apelsinrestapi.repository;

import com.example.apelsinrestapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByActiveTrue();

    @Query(value = "select * from customer where id not in (select customer_id from orders where date between '2020-01-01' and '2023-01-01')",nativeQuery = true)
    List<Customer> customersWithoutOrders();

    @Query(value = "select * from customer c join orders o on c.id = o.customer_id where o.date between '2022-01-01' and '2023-01-01'", nativeQuery = true)
    List<Customer> getCustomer();
}
