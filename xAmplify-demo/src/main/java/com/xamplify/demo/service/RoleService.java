package com.xamplify.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xamplify.demo.modal.Role;
import com.xamplify.demo.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
    private RoleRepository roleRepository;

    public void saveRole(Role role) {
        roleRepository.save(role);
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
