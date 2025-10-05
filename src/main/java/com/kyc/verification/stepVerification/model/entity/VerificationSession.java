package com.kyc.verification.stepVerification.model.entity;

import com.kyc.verification.company.model.entity.Company;
import com.kyc.verification.stepVerification.model.enums.VerificationStepEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "verification_session")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "tracking_code", nullable = false, unique = true)
    private String trackingCode;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "national_code", length = 10, nullable = false)
    private String nationalCode;

    @Column(name = "mobile_number", length = 11, nullable = false)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_step", nullable = false)
    private VerificationStepEnum currentStep = VerificationStepEnum.SHAHKAR;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted = false;

    @Lob
    @Column(name = "failed_reason")
    private String failedReason;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VerificationStepResult> stepResults;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
