package com.kyc.verification.stepVerification.repository;

import com.kyc.verification.stepVerification.model.entity.VerificationStepResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerificationStepResultRepository extends JpaRepository<VerificationStepResult, Long> {

    List<VerificationStepResult> findBySessionId(Long sessionId);

}
