package com.example.apelsinrestapi.service;

import com.example.apelsinrestapi.dto.ApiResponse;
import com.example.apelsinrestapi.entity.Customer;
import com.example.apelsinrestapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record CustomerService(CustomerRepository customerRepository) {

    public ApiResponse edit(Integer id, Customer customer) {
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("NOT FOUND", false);
        }
        Customer edit = byId.get();
        edit.setAddress(customer.getAddress());
        edit.setCountry(customer.getCountry());
        edit.setName(customer.getName());
        edit.setPhone(customer.getPhone());

        return new ApiResponse("EDITED", true, customerRepository.save(edit));
    }
}
