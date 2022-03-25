package com.example.apelsinrestapi.service;

import com.example.apelsinrestapi.dto.ApiResponse;
import com.example.apelsinrestapi.dto.OverPaidInvoice;
import com.example.apelsinrestapi.dto.WrongDateInvoice;
import com.example.apelsinrestapi.entity.Invoice;
import com.example.apelsinrestapi.entity.Payment;
import com.example.apelsinrestapi.repository.InvoiceRepository;
import com.example.apelsinrestapi.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record InvoiceService(InvoiceRepository invoiceRepository, PaymentRepository paymentRepository) {

    public ApiResponse getWrongDateInvoices() {
        List<Invoice> invoiceList = invoiceRepository.wrongDateInvoices();
        List<WrongDateInvoice> collect = invoiceList.stream().map(this::invoiceToInvoice).toList();
        return new ApiResponse("Mana", true, collect);
    }

    public WrongDateInvoice invoiceToInvoice(Invoice invoice) {
        return new WrongDateInvoice(
                invoice.getId(),
                invoice.getIssued(),
                invoice.getOrder().getId(),
                invoice.getOrder().getDate()
        );
    }

    public ApiResponse getOverPaidInvoices() {
        List<Invoice> invoices = invoiceRepository.overPaidInvoices();
        List<OverPaidInvoice> overPaidInvoices = invoices.stream().map(this::invoiceToOverPaidInvoice).toList();
        return new ApiResponse("Overpaid invoices", true, overPaidInvoices);
    }

    public OverPaidInvoice invoiceToOverPaidInvoice(Invoice invoice) {
        double sum = 0;
        double reimbursedAmount = 0;
        List<Payment> paymentList = paymentRepository.getAllPaymentByInvoice_Id(invoice.getId());
        for (Payment payment : paymentList) {
            if (payment.getAmount() > 0) {
                sum += payment.getAmount();
            }
        }
        if (sum > invoice.getAmount()) reimbursedAmount = sum - invoice.getAmount();
        return new OverPaidInvoice(invoice.getId(), reimbursedAmount);
    }
}
