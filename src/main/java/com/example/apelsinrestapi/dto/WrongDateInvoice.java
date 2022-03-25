package com.example.apelsinrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WrongDateInvoice {
    private Integer id;
    private Timestamp issued;
    private Integer orderId;
    private Timestamp orderDate;
}
