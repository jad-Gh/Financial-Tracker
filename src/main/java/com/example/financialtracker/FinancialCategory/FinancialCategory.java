package com.example.financialtracker.FinancialCategory;

import com.example.financialtracker.AppUser.AppUser;
import com.example.financialtracker.FinancialAccount.FinancialAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "category_owner_id")
    private AppUser categoryOwner;


}
