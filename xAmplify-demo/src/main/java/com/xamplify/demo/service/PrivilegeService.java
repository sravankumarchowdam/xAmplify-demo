package com.xamplify.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xamplify.demo.modal.Privilege;
import com.xamplify.demo.repository.PrivilegeRepository;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public List<Privilege> getAllPrivileges() {
        return privilegeRepository.findAll();
    }

    public Privilege getPrivilegeById(Long id) {
        return privilegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Privilege not found"));
    }

    @Transactional
    public String savePrivilege(Privilege privilege) {
        if (privilege.getId() == null) { // New privilege
            if (privilegeRepository.existsByNameIgnoreCase(privilege.getName())) {
                return "Privilege name already exists.";
            }
        } else { // Updating an existing privilege
            if (privilegeRepository.existsByNameIgnoreCaseAndNotId(privilege.getName(), privilege.getId())) {
                return "Privilege name already exists.";
            }
        }

        privilege.setName(privilege.getName().toUpperCase());
        privilegeRepository.save(privilege);
        return "Privilege saved successfully.";
    }


    @Transactional
    public void updatePrivilege(Long id, Privilege privilege) {
        Privilege existingPrivilege = getPrivilegeById(id);
        existingPrivilege.setName(privilege.getName().toUpperCase());
        privilegeRepository.save(existingPrivilege);
    }

    @Transactional
    public void deletePrivilege(Long id) {
        privilegeRepository.deleteById(id);
    }
}
