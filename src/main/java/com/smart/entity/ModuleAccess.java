package com.smart.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ModuleAccess  extends BaseEntity{
	
	private String moduleId;
	private String  userId;
	private boolean canView; // can view module
	private boolean canEdit;
	private boolean canCreate;
	private boolean canDelete;
	private boolean canViewAll;
	

}
