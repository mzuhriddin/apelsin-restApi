package com.example.apelsinrestapi.service;

import com.example.apelsinrestapi.dto.ApiResponse;
import com.example.apelsinrestapi.dto.PaymentDto;
import com.example.apelsinrestapi.entity.Invoice;
import com.example.apelsinrestapi.entity.Payment;
import com.example.apelsinrestapi.repository.InvoiceRepository;
import com.example.apelsinrestapi.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record PaymentService(PaymentRepository paymentRepository, InvoiceRepository invoiceRepository) {
    public ApiResponse add(PaymentDto paymentDto) {
        Optional<Invoice> byId = invoiceRepository.findById(paymentDto.getInvoiceId());
        if (byId.isEmpty()) {
            return new ApiResponse("NOT FOUND", false);
        }
        Payment payment = new Payment();
        payment.setAmount(paymentDto.getAmount());
        payment.setInvoice(byId.get());
        paymentRepository.save(payment);

        return new ApiResponse("ADDED", true);
    }
}

