package com.kyc.verification.stepVerification.model.entity;

import com.kyc.verification.stepVerification.model.enums.VerificationStepEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_step_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationStepResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private VerificationSession session;

    @Enumerated(EnumType.STRING)
    @Column(name = "step_name", nullable = false)
    private VerificationStepEnum stepName;

    @Column(name = "is_passed", nullable = false)
    private Boolean isPassed;

    @Column(name = "data_storage_path", length = 512)
    private String dataStoragePath;

    @Column(name = "step_details", columnDefinition = "LONGTEXT")
    private String details;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Lob
    @Column(name = "video_data", columnDefinition = "LONGTEXT")
    private String videoData;

    @Lob
    @Column(name = "sign_data", columnDefinition = "LONGTEXT")
    private String signData;

    @PrePersist
    protected void onCreate() {
        this.verifiedAt = LocalDateTime.now();
    }
}