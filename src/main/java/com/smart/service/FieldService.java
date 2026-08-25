package com.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.entity.ModuleField;
import com.smart.helper.service.CurrentUserService;
import com.smart.repository.FieldRepository;

@Service
public class FieldService {

	@Autowired
	CurrentUserService currentUserService;

	@Autowired
	private FieldRepository fieldRepository;

	public ModuleField createField(ModuleField field) {

		field.setCompanyId(currentUserService.getCurrentUser().getCompanyId());

		   if (field.getFieldKey() == null || field.getFieldKey().isBlank()) {
		        field.setFieldKey(generateFieldKey(field.getFieldName()));
		    }
		return fieldRepository.save(field);
	}

	public List<ModuleField> findByModuleId(String moduleId) {

		return fieldRepository.findByModuleIdOrderByDisplayOrder(moduleId);

	}
	
	private String generateFieldKey(String fieldName) {

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

	public ModuleField updateField(ModuleField moduleField) {
		  if (moduleField.getFieldKey() == null || moduleField.getFieldKey().isBlank()) {
			  moduleField.setFieldKey(generateFieldKey(moduleField.getFieldName()));
		    }
		return fieldRepository.save(moduleField);
	}

	public String deleteFieldById(String id) {
	fieldRepository.deleteById(id);
		return "Deleted SuccessFully";
	}


}
