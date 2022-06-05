package com.example.financialtracker.AppUser;


import com.example.financialtracker.ConfirmationToken.ConfirmationToken;
import com.example.financialtracker.FinancialCategory.FinancialCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false,unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonProperty("DOB")
    private LocalDate DOB;

    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "categoryOwner")
    @JsonIgnoreProperties("categoryOwner")
    private List<FinancialCategory> categoryList;

}
