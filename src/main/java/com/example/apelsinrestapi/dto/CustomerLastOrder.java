package com.example.apelsinrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerLastOrder {
    private Integer customerId;
    private String customerName;
    private Timestamp orderDate;
}
