package com.example.apelsinrestapi.repository;

import com.example.apelsinrestapi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findAllByActiveTrue();

    List<Payment> getAllPaymentByInvoice_Id(Integer id);
}
