package com.xamplify.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.xamplify.demo.exception.DuplicateRoleException;
import com.xamplify.demo.modal.Role;
import com.xamplify.demo.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
    private RoleRepository roleRepository;

	public void saveRole(Role role) {
        try {
            roleRepository.save(role);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("xa_role_name_key")) {
                throw new DuplicateRoleException("Role '" + role.getName() + "' already exists.");
            }
            throw ex; // rethrow for other unexpected exceptions
        }
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

}
