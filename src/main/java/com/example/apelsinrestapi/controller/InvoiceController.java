package com.example.apelsinrestapi.controller;

import com.example.apelsinrestapi.dto.ApiResponse;
import com.example.apelsinrestapi.entity.Invoice;
import com.example.apelsinrestapi.repository.InvoiceRepository;
import com.example.apelsinrestapi.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {
    final InvoiceRepository invoiceRepository;
    final InvoiceService invoiceService;

    @GetMapping("/expired_invoices")
    public HttpEntity<?> getExpiredInvoices() {
        List<Invoice> invoices = invoiceRepository.expiredInvoices();
        return ResponseEntity.ok().body(invoices);
    }

    @GetMapping("/wrong_date_invoices")
    public HttpEntity<?> getWrongDateInvoices() {
        ApiResponse wrongDateInvoices = invoiceService.getWrongDateInvoices();
        return ResponseEntity.ok().body(wrongDateInvoices);
    }

    @GetMapping("/overpaid_invoices")
    public HttpEntity<?> getOverPaidInvoices() {
        ApiResponse response = invoiceService.getOverPaidInvoices();
        return ResponseEntity.ok().body(response);
    }

}
