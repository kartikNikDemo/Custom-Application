package com.smart.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SingleRecordValueUpdate {
	
	private String recordId;
	private String moduleId;
	private String moduleFieldKey;
	private String newValue;

}
