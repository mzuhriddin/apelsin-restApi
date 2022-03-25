package com.example.apelsinrestapi.controller;

import com.example.apelsinrestapi.dto.ApiResponse;
import com.example.apelsinrestapi.dto.OrderDto;
import com.example.apelsinrestapi.entity.Order;
import com.example.apelsinrestapi.repository.OrderRepository;
import com.example.apelsinrestapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    final OrderRepository orderRepository;
    final OrderService orderService;

    @GetMapping
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(orderRepository.findAllByActiveTrue());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return ResponseEntity.status(optionalOrder.isEmpty() ? 404 : 200).body(optionalOrder.orElse(new Order()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody OrderDto orderDto) {
        ApiResponse apiResponse = orderService.add(orderDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Order order = byId.get();
        order.setActive(false);
        orderRepository.save(order);
        return ResponseEntity.ok().body("DELETED");
    }

    @GetMapping("/orders_without_details")
    public HttpEntity<?> getOrdersWithoutDetails(){
        return ResponseEntity.ok().body(orderRepository.ordersWithoutDetails());
    }

    @GetMapping("/customers_last_orders")
    public HttpEntity<?> getCustomerLastOrder() {
        ApiResponse response = orderService.getCustomerLastOrder();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/number_of_products_in_year")
    public HttpEntity<?> getYearOrders() {
        ApiResponse response = orderService.getYearOrders();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/orders_without_invoices")
    public HttpEntity<?> getGeneralReports() {
        ApiResponse response = orderService.getGeneralReports();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/details")
    public HttpEntity<?> getDetailsByOrderId(@RequestParam Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Order order = orderOptional.get();
        ApiResponse response = new ApiResponse("Mana", true, order);
        return ResponseEntity.ok().body(response);
    }
}
