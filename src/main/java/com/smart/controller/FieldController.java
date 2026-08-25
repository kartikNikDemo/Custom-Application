package com.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.entity.ModuleField;
import com.smart.service.FieldService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/field")
public class FieldController {
	@Autowired
	private  FieldService fieldService;
	
	@PostMapping("/create")
	public ResponseEntity<ModuleField> createField(@RequestBody ModuleField field){
		
		return ResponseEntity.ok(fieldService.createField(field));
	}
	
	@GetMapping("/getByModuleId/{moduleId}")
	public ResponseEntity<List<ModuleField>> getByModuleId(@PathVariable String moduleId){
		
		
		return ResponseEntity.ok(fieldService.findByModuleId(moduleId));
	}
	
	@PutMapping("/update")
	public ResponseEntity<ModuleField> updateField(@RequestBody ModuleField moduleField){
		
		
		return ResponseEntity.ok(fieldService.updateField(moduleField));
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteFieldById(@PathVariable String id){
		
		
		return ResponseEntity.ok(fieldService.deleteFieldById(id));
	}
	
}
