package com.smart.entity;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Records  extends BaseEntity {
	
	private String module;
	private String moduleId;
	
}
