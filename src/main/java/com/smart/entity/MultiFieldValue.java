package com.smart.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MultiFieldValue extends BaseEntity{
    private String moduleFieldId;
    private String moduleId;
	private String value;
	private String colour;
	private int displayOrder;
	
	

}
