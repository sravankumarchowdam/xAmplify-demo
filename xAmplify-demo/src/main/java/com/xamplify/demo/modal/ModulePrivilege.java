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
@Table(name = "xa_module_privilege",
       uniqueConstraints = { @UniqueConstraint(columnNames = { "module_id", "privilege_id" }) }) // ✅ Ensures uniqueness
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModulePrivilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Uses auto-incremented BigInteger
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module; // ✅ Links to a module

    @ManyToOne
    @JoinColumn(name = "privilege_id", nullable = false)
    private Privilege privilege; // ✅ Links to a privilege

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
