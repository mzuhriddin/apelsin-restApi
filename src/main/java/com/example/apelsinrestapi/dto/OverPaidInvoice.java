package com.example.apelsinrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OverPaidInvoice {
    private Integer invoiceId;
    private double reimbursedAmount;

}
