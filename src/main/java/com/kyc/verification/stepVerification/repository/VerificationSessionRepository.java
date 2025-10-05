package com.kyc.verification.stepVerification.repository;

import com.kyc.verification.company.model.entity.Company;
import com.kyc.verification.stepVerification.model.entity.VerificationSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationSessionRepository extends JpaRepository<VerificationSession, Long> {

    Optional<VerificationSession> findByTrackingCode(String trackingCode);

    List<VerificationSession> findAllByCompanyId(Long companyId);

    List<VerificationSession> findAllByCompany(Company company);

}
