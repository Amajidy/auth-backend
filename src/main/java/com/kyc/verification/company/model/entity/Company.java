package com.kyc.verification.company.model.entity;

import com.kyc.verification.stepVerification.model.entity.VerificationSession;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "admin_email", nullable = false, unique = true)
    private String adminEmail;

    @Column(name = "admin_mobile_number", nullable = false, unique = true, length = 15)
    private String adminMobileNumber;

    @Column(name = "api_key", nullable = false, unique = true)
    private String apiKey;

    @Column(name = "callback_url", nullable = false)
    private String callbackUrl;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VerificationSession> sessions;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}