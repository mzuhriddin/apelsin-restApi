package com.example.apelsinrestapi.controller;

import com.example.apelsinrestapi.dto.ApiResponse;
import com.example.apelsinrestapi.entity.Payment;
import com.example.apelsinrestapi.dto.PaymentDto;
import com.example.apelsinrestapi.repository.PaymentRepository;
import com.example.apelsinrestapi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    final PaymentRepository paymentRepository;
    final PaymentService paymentService;

    @GetMapping
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(paymentRepository.findAllByActiveTrue());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        return ResponseEntity.status(optionalPayment.isEmpty() ? 404 : 200).body(optionalPayment.orElse(new Payment()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody PaymentDto paymentDto) {
        ApiResponse apiResponse = paymentService.add(paymentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Payment> byId = paymentRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Payment payment = byId.get();
        payment.setActive(false);
        paymentRepository.save(payment);
        return ResponseEntity.ok().body("DELETED");
    }

    @GetMapping("/details")
    public HttpEntity<?> getDetailsByPaymentId(@RequestParam Integer paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Payment payment = optionalPayment.get();
        ApiResponse response = new ApiResponse("Mana", true, payment);
        return ResponseEntity.ok().body(response);
    }
}
