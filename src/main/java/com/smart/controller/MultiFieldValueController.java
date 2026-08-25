package com.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.entity.MultiFieldValue;
import com.smart.service.MultiFieldValueService;


@RestController
@RequestMapping("/multiFieldValue")
public class MultiFieldValueController {
	@Autowired
    private MultiFieldValueService multiFieldValueService;
	
	@PostMapping("/create")
	public ResponseEntity<List<MultiFieldValue>> createMultiFieldValue(@RequestBody List<MultiFieldValue> request){
		
         return ResponseEntity.ok(multiFieldValueService.create(request));
	}
	
	@PostMapping("/bulkUpdate")
	public ResponseEntity<List<MultiFieldValue>> bulkUpdate(@RequestBody List<MultiFieldValue> request){
		
         return ResponseEntity.ok(multiFieldValueService.bulkUpdate(request));
	}
	
	
	@PostMapping("/update")
	public ResponseEntity<MultiFieldValue> updateMultiFieldValue(MultiFieldValue request){
		
         return ResponseEntity.ok(multiFieldValueService.update(request));
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable String id){
		
         return ResponseEntity.ok(multiFieldValueService.delete(id));
	}
	

	@GetMapping("/getByFieldId/{id}")
	public ResponseEntity<List<MultiFieldValue>> getById(@PathVariable String id){
		
         return ResponseEntity.ok(multiFieldValueService.getByFieldId(id));
	}
	
    
}
