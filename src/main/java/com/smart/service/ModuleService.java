package com.smart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.entity.Module;
import com.smart.helper.service.CurrentUserService;
import com.smart.repository.ModuleRepository;

@Service
public class ModuleService {

	@Autowired
	private CurrentUserService currentUserService;
	@Autowired
	private ModuleRepository moduleRepository;

	public Module createModule(Module module) {

		module.setCompanyId(currentUserService.getCurrentUser().getCompanyId());
		
		   
		module.setModuleKey(generateKey(module.getName()));
		    

		return moduleRepository.save(module);
	}

	public Module getById(String id) {

		Optional<Module> module = moduleRepository.findById(id);

		return module.get();
	}

	public List<Module> getModule() {
		
		

		return moduleRepository.findByCompanyIdOrderByDisplayOrderAsc(currentUserService.getCurrentUser().getCompanyId());
	}
	
	
	private String generateKey(String fieldName) {

	    if (fieldName == null || fieldName.isBlank()) {
	        return "";
	    }

	    String[] words = fieldName.trim().split("\\s+");

	    StringBuilder key = new StringBuilder(words[0].toLowerCase());

	    for (int i = 1; i < words.length; i++) {
	        key.append(words[i].substring(0, 1).toUpperCase())
	           .append(words[i].substring(1).toLowerCase());
	    }

	    return key.toString();
	}

	  public Module updateModule(Module module) {
		  module.setCompanyId(currentUserService.getCurrentUser().getCompanyId());
		return moduleRepository.save(module);
	}

	  public Module getModuleById(String id) {
	
		return moduleRepository.findById(id).get();
	  }

	  public String deleteModule(String id) {
		  moduleRepository.deleteById(id);
		return "Module Deleted Successfully";
	  }

}
