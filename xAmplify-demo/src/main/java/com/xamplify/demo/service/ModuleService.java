package com.xamplify.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xamplify.demo.modal.Module;
import com.xamplify.demo.modal.Privilege;
import com.xamplify.demo.repository.ModuleRepository;
import com.xamplify.demo.repository.PrivilegeRepository;

@Service
public class ModuleService {

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Transactional
	public String saveModule(Module module, List<String> privilegeNames) {
		Module existingModule;

		if (module.getId() != null) { // ✅ Check if module exists for updating
			existingModule = moduleRepository.findById(module.getId())
					.orElseThrow(() -> new RuntimeException("Module not found"));
			existingModule.setName(module.getName().toUpperCase());
			existingModule.getPrivileges().clear(); // ✅ Remove old privileges
		} else {
			if (moduleRepository.existsByNameIgnoreCase(module.getName())) {
				return "Module name already exists.";
			}
			existingModule = new Module(); // ✅ Initialize first
			existingModule.setName(module.getName().toUpperCase());
		}

		// ✅ Add new privileges
		Set<Privilege> privileges = new HashSet<>();
		for (String privilegeName : privilegeNames) {
			Privilege privilege = new Privilege();
			privilege.setName(privilegeName.toUpperCase());
			privilege.setModule(existingModule);
			privileges.add(privilege);
		}
		existingModule.setPrivileges(privileges);

		moduleRepository.save(existingModule);
		return "Module and privileges saved successfully.";
	}

	@Transactional
	public String updateModule(Long id, Module module, List<String> privilegeNames) {
		Module existingModule = moduleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Module not found"));

		existingModule.setName(module.getName().toUpperCase());

		// Remove old privileges
		existingModule.getPrivileges().clear();

		// Add new privileges
		Set<Privilege> privileges = privilegeNames.stream().map(privilegeName -> {
			Privilege privilege = new Privilege();
			privilege.setName(privilegeName.toUpperCase());
			privilege.setModule(existingModule);
			return privilege;
		}).collect(Collectors.toSet());

		existingModule.setPrivileges(privileges);
		moduleRepository.save(existingModule);
		return "Module and privileges updated successfully.";
	}

	@Transactional
	public void deleteModule(Long id) {
		moduleRepository.deleteById(id);
	}

	public List<Module> getAllModules() {
		return moduleRepository.findAll();
	}
}
