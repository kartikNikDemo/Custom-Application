package com.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smart.entity.ModuleAccess;
import com.smart.service.ModuleAccessService;

@RestController
@RequestMapping("/moduleAccess")
public class ModuleAccessController {
	
	
	@Autowired
	ModuleAccessService moduleAccessService;
	
	
	@PostMapping("/create")
	public ResponseEntity<ModuleAccess> createModuleAccessc(@RequestBody ModuleAccess moduleAccess){
		
		return ResponseEntity.ok(moduleAccessService.createModuleAccess(moduleAccess));
	}
	
	@PutMapping("/update")
	public ResponseEntity<ModuleAccess> updateModuleAccess(@RequestBody ModuleAccess moduleAccess){
		
		return ResponseEntity.ok(moduleAccessService.updateModuleAccess(moduleAccess));
	}
	
	@GetMapping("/getByUserId/{userId}")
	public ResponseEntity<List<ModuleAccess>>  getByUserId(@PathVariable String userId){
		
		return ResponseEntity.ok(moduleAccessService.getByUserId(userId));
	}
	
	
	@GetMapping("/getByModuleIdAndUserId")
	public ResponseEntity<ModuleAccess>  getByModuleIdAndUserId(@RequestParam String moduleId,@RequestParam String userId){
		
		return ResponseEntity.ok(moduleAccessService.getByModuleIdAndUserId(moduleId,userId));
	}	
	
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<ModuleAccess>  getById(@PathVariable String id){
		
		return ResponseEntity.ok(moduleAccessService.getById(id));
	}	
		
	}


