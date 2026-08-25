package com.smart.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreatePublicRecord {
	private String moduleId;
	private List<FieldIdAndValue> fieldAndValue;
}
