package com.xamplify.demo.modal;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigInteger;

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
	private BigInteger id;

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
