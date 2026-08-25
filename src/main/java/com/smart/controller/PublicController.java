package com.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dto.CreatePublicRecord;
import com.smart.entity.ModuleField;
import com.smart.service.FieldService;
import com.smart.service.RecordFieldValueService;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	RecordFieldValueService recordFieldValueService;
	
	@Autowired
	private  FieldService fieldService;
	
	@PostMapping("/create/{companyId}")
	public ResponseEntity<?> createPublicRecord(@PathVariable String companyId, @RequestBody CreatePublicRecord createPublicRecord){
		
		return ResponseEntity.ok(recordFieldValueService.createPublicRecord(companyId,createPublicRecord));
	}
	
	@GetMapping("/getByModuleId/{moduleId}")
	public ResponseEntity<List<ModuleField>> getByModuleId(@PathVariable String moduleId){
		
		
		return ResponseEntity.ok(fieldService.findByModuleId(moduleId));
	}

}
