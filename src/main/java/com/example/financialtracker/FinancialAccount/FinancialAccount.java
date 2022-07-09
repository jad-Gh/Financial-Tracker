package com.example.financialtracker.FinancialAccount;

import com.example.financialtracker.AppUser.AppUser;
import com.example.financialtracker.FinancialRecord.FinancialRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FinancialAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    private double balance;

    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;

//    @ManyToOne()
//    @JoinColumn(name = "account_owner_id")
//    private AppUser accountOwner;

//    @OneToMany(mappedBy = "accountRef")
//    private List<FinancialRecord> financialRecordList;



}
