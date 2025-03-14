package com.xamplify.demo.modal;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigInteger;

@Entity
@Table(name = "xa_role", uniqueConstraints = { @UniqueConstraint(columnNames = "name") }) // ✅ Enforces unique names
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Uses auto-incremented BigInteger
	private BigInteger id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false, updatable = false)
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();

	// ✅ Ensure name is always uppercase before saving
	@PrePersist
	@PreUpdate
	public void normalizeRoleName() {
		this.name = this.name.toUpperCase();
	}

	// ✅ Returns role name prefixed with "ROLE_" for Spring Security
	public String getAuthority() {
		return "ROLE_" + name;
	}
}
