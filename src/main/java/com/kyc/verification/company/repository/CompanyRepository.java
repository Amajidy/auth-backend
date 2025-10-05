package com.kyc.verification.company.repository;

import com.kyc.verification.company.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByApiKey(String apiKey);

    Optional<Company> findByAdminMobileNumber(String adminMobile);

}
