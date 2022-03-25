package com.example.apelsinrestapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Order order;

    @Column(nullable = false)
    private double amount;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp issued;

    @Column(nullable = false, updatable = false)
    private Timestamp due;
    private boolean active = true;
}
