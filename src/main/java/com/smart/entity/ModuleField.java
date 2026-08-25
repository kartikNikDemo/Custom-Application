package com.smart.entity;

import com.smart.Enum.FIELD_TYPE;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ModuleField extends BaseEntity {

	private String moduleId;

	private String fieldName;
	
	private String fieldKey;

	@Enumerated(EnumType.STRING)
	private FIELD_TYPE fieldType;

	private boolean required;

	private boolean uniqueField;

	private String defaultValue;

	private Integer displayOrder;

	private boolean multiValue;
	
	private boolean visibleInList;
	
	private boolean visibleInCreate;
	
	private boolean visibleInEdit;
	
	private boolean releatedTo; 
	
	private String relatedToModuleId;
	
	private String relatedToModuleFieldId;
	

}
