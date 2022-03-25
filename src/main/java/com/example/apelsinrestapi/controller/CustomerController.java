package com.example.apelsinrestapi.controller;

import com.example.apelsinrestapi.dto.ApiResponse;
import com.example.apelsinrestapi.entity.Customer;
import com.example.apelsinrestapi.repository.CustomerRepository;
import com.example.apelsinrestapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    final CustomerRepository customerRepository;
    final CustomerService customerService;

    @GetMapping
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(customerRepository.findAllByActiveTrue());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return ResponseEntity.status(optionalCustomer.isEmpty() ? 404 : 200).body(optionalCustomer.orElse(new Customer()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody Customer customer) {
        Customer save = customerRepository.save(customer);
        return ResponseEntity.ok().body(save);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody Customer customer) {
        ApiResponse edit = customerService.edit(id, customer);
        return ResponseEntity.status(edit.isSuccess() ? 200 : 409).body(edit);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Customer customer = byId.get();
        customer.setActive(false);
        customerRepository.save(customer);
        return ResponseEntity.ok().body("DELETED");
    }

    @GetMapping("/customers_without_orders")
    public HttpEntity<?> getCustomersWithoutOrders() {
        List<Customer> customers = customerRepository.customersWithoutOrders();
        return ResponseEntity.ok().body(customers);
    }
}
