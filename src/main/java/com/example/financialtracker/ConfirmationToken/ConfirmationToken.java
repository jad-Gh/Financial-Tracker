package com.example.financialtracker.ConfirmationToken;

import com.example.financialtracker.AppUser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(updatable = false,nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private String uuid;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private AppUser user;

}
