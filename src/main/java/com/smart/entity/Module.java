package com.smart.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Module extends BaseEntity{
	
	private String name;
	private String description;
	private String icon;
	private String color;
	private String moduleKey;
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_module_id")
    private List<Module> subModule;
	private int displayOrder;
	

}
