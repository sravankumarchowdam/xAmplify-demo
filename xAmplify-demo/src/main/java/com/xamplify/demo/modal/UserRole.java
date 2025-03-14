package com.xamplify.demo.modal;

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
@Table(name = "xa_user_role", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_company_id", "role_id" }) }) // ✅
																															// Ensures
																															// uniqueness
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Uses auto-incremented BigInteger
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_company_id", nullable = false)
	private UserCompany userCompany; // ✅ Uses existing user-company mapping

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@Column(nullable = false)
	@Builder.Default
	private LocalDateTime assignedAt = LocalDateTime.now();
}
