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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xa_module", uniqueConstraints = { @UniqueConstraint(columnNames = "name") }) // ✅ Unique case-sensitive
																							// names
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Uses auto-incremented BigInteger
	private Long id;

	@Column(nullable = false, unique = true,columnDefinition = "CITEXT UNIQUE NOT NULL")
	private String name; // ✅ Global module name (case-sensitive)

	@Column(nullable = false, updatable = false)
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();

	// ✅ Mapped with CompanyModules
	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CompanyModule> companyModules;
}
