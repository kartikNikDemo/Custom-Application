package com.smart.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RecordFieldValue extends BaseEntity {
	
	private  String  recordId;
	private String module;
	private String moduleId;
	private String field;
	private String value;

}
