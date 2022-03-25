package com.example.apelsinrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustResp {
    private Integer id;
    private String name;
    private Timestamp date;
}
