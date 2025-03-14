package com.xamplify.demo.modal;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xa_company_module_privilege", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "company_id", "module_privilege_id" }) }) // ✅ Ensures uniqueness
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyModulePrivilege {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Uses auto-incremented BigInteger
	private Long id;

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company; // ✅ Links to a company

	@ManyToOne
	@JoinColumn(name = "module_privilege_id", nullable = false)
	private ModulePrivilege modulePrivilege; // ✅ Links to a module-privilege

	@OneToMany(mappedBy = "companyModulePrivilege", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserCompanyPrivilege> userCompanyPrivileges;

	@Column(nullable = false, updatable = false)
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
}
