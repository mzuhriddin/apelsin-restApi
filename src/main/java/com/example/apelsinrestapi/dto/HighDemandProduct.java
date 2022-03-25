package com.example.apelsinrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HighDemandProduct {
    private Integer id;
    private String name;
    private int orderQuantity;
}
