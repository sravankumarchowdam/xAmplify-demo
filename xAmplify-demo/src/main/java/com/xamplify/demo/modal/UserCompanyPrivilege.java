package com.xamplify.demo.modal;

import java.math.BigInteger;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xa_user_company_privilege",
       uniqueConstraints = { @UniqueConstraint(columnNames = { "user_company_id", "company_module_privilege_id" }) }) // ✅ Ensures uniqueness
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCompanyPrivilege {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Uses auto-incremented BigInteger
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "user_company_id", nullable = false)
    private UserCompany userCompany; // ✅ Links user to company

    @ManyToOne
    @JoinColumn(name = "company_module_id", nullable = false)
    private CompanyModule companyModule; // ✅ Links module to company

    @ManyToOne
    @JoinColumn(name = "company_module_privilege_id", nullable = false)
    private CompanyModulePrivilege companyModulePrivilege; // ✅ Links privilege to module & company

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime assignedAt = LocalDateTime.now();

}
