package com.xamplify.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xamplify.demo.exception.DuplicateRoleException;
import com.xamplify.demo.modal.Role;
import com.xamplify.demo.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public void saveRole(Role role) {
		if (roleRepository.existsByNameIgnoreCase(role.getName())) {
			throw new DuplicateRoleException("Role '" + role.getName() + "' already exists!");
		}
		role.setName(role.getName().toUpperCase()); // Enforce uppercase storage
		roleRepository.save(role);
	}
	
	public void updateRole(Role role) {
        Role existingRole = roleRepository.findById(role.getId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + role.getId()));

        // Check for name duplication but allow same name for the current role
        if (!existingRole.getName().equalsIgnoreCase(role.getName()) &&
                roleRepository.existsByNameIgnoreCase(role.getName())) {
            throw new DuplicateRoleException("Role '" + role.getName() + "' already exists!");
        }

        existingRole.setName(role.getName().toUpperCase());
        roleRepository.save(existingRole);
    }

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	public Role getRoleById(Long id) {
		return roleRepository.findById(id).orElse(null);
	}

	public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found with ID: " + id);
        }
        
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot delete role, it might be assigned to users.");
        }
    }

}
