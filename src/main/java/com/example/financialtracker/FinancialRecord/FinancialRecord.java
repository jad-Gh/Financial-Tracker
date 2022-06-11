package com.example.financialtracker.FinancialRecord;

import com.example.financialtracker.FinancialCategory.FinancialCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "financial_category_id")
    @JsonIgnoreProperties(value = "categoryOwner")
    private FinancialCategory financialCategory;

    @Column(nullable = false)
    private double value;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String description;

    private boolean recurrent;


}
