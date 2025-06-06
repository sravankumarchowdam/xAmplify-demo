package com.xamplify.demo.modal;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private Long id;

	@Column(nullable = false, unique = true, columnDefinition = "TEXT CHECK (name = UPPER(name))")
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
