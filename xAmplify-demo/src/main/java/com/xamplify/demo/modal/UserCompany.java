package com.xamplify.demo.modal;

import java.math.BigInteger;
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
@Table(name = "xa_user_company", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "company_id" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCompany {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@OneToMany(mappedBy = "userCompany", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserCompanyPrivilege> userCompanyPrivileges; // ✅ Correct mapping

	@Column(nullable = false)
	@Builder.Default
	private LocalDateTime assignedAt = LocalDateTime.now();

}
