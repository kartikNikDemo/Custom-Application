package com.smart.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRecord {
	
	private String moduleId;
	private String recordId;
	private  List<RecordFieldValueIdandValue> recordFieldValueIdandValue;

}
