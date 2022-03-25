package com.example.apelsinrestapi.repository;

import com.example.apelsinrestapi.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findAllByActiveTrue();

    @Query(value = "select * from invoice i where i.active = true and i.issued > i.due", nativeQuery = true)
    List<Invoice> expiredInvoices();

    @Query(value = "select i.* from invoice i inner join orders o on o.id = i.order_id where i.issued<o.date ",nativeQuery = true)
    List<Invoice> wrongDateInvoices();

    @Query(value = "select * from invoice i where i.amount < (select sum(p.amount) from payment p where i.id = p.invoice_id)",nativeQuery = true)
    List<Invoice> overPaidInvoices();
}
