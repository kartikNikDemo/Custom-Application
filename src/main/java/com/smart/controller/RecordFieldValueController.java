package com.smart.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dto.CreateRecords;
import com.smart.dto.request.ModuleFilter;
import com.smart.entity.RecordFieldValue;
import com.smart.service.RecordFieldValueService;
import com.smart.dto.FieldIdAndValue;
import com.smart.dto.RecordFieldValueIdandValue;
import com.smart.dto.RecordIdAndValue;
import com.smart.dto.SingleRecordValueUpdate;
import com.smart.dto.UpdateRecord;
@RestController
@RequestMapping("/recordFieldValue")
public class RecordFieldValueController {
	
	@Autowired
	RecordFieldValueService recordFieldValueService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Map<String,String>> createRecord(@RequestBody CreateRecords record){
		
		return ResponseEntity.ok(recordFieldValueService.createRecord(record));
	}
	
	@PutMapping("/getAllDataByModuleId")  // get data by list
	public ResponseEntity<Object> getDataByModule(@RequestParam String moduleId ,@RequestParam(required = false) String query,
			                @RequestBody(required = false) ModuleFilter filter,Pageable pageable){
		
		return ResponseEntity.ok(recordFieldValueService.getDataByModule(moduleId,query,filter,pageable));
	}
	
	@GetMapping("/getModuleDataById")
	public ResponseEntity<Map<String ,Object>> getModuleDataByRecordId(@RequestParam String moduleId ,@RequestParam String id ){
		// note id = record id
		return ResponseEntity.ok(recordFieldValueService.getModuleDataByRecordId(moduleId,id));
	}
	

	@GetMapping("/getFieldIdByRecordId/{recordId}")
	public ResponseEntity<List<RecordFieldValue>> getFieldIdByRecordId(@PathVariable String recordId  ){
		// note id = record id
		return ResponseEntity.ok(recordFieldValueService.getFieldIdByRecordId(recordId));
	}
	
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object> > bulkUpdateRecordFieldValue(@RequestBody UpdateRecord updateRecord  ){
		// note id = record id
		return ResponseEntity.ok(recordFieldValueService.bulkUpdateRecordFieldValue(updateRecord));
	}
	
	
	@GetMapping("/getRecordDataForEdit/{recordId}")
	public ResponseEntity<List<RecordFieldValueIdandValue>> getRecordDataForEdit(@PathVariable String recordId  ){
		// note id = record id
		return ResponseEntity.ok(recordFieldValueService.getRecordDataForEdit(recordId));
	}

   
	@PutMapping("/updateSingleRecordValue")
	public ResponseEntity<String> updaetSingleRecordValue(@RequestBody SingleRecordValueUpdate update){
		
		
		return ResponseEntity.ok(recordFieldValueService.updaetSingleRecordValue(update));
	}
	
    @DeleteMapping("/deleteByRecordId/{recordId}")
	public ResponseEntity<String> deleteByRecordId(@PathVariable String recordId){
    	
    	return ResponseEntity.ok(recordFieldValueService.deleteByRecordId(recordId));
    }
    
    @GetMapping("/getRecordIdAndValue")
    public ResponseEntity<List<RecordIdAndValue>> getRecordIdAndValue(@RequestParam String moduleId,@RequestParam String fieldId){
    	
    	return ResponseEntity.ok(recordFieldValueService.getRecordIdAndValue(moduleId,fieldId));
    }
}
