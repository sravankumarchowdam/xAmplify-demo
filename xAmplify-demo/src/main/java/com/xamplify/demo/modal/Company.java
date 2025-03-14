package com.xamplify.demo.modal;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xa_company") // ✅ Explicitly maps to prefixed table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Uses auto-incremented BigInteger
	private Long id;

	@Column(nullable = false, unique = true, columnDefinition = "CITEXT")
	private String name; // ✅ Case-insensitive due to PostgreSQL CITEXT

	@Column(nullable = false, unique = true, columnDefinition = "TEXT CHECK (domain_name = LOWER(domain_name))")
	private String domainName; // ✅ Always stored in lowercase

	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CompanyModule> companyModules;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CompanyModulePrivilege> companyModulePrivileges;

}
