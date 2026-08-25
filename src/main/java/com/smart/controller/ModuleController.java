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

import com.smart.entity.Module;
import com.smart.service.ModuleService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/module")
public class ModuleController {

	@Autowired
	private ModuleService moduleService;

	@RolesAllowed("ADMIN")
	@PostMapping("/create")
	public ResponseEntity<Module> createModule(@RequestBody Module module) {

		return ResponseEntity.ok(moduleService.createModule(module));
	}
	
	@GetMapping("/getAllModule")
	public ResponseEntity<List<Module>> getModule() {

		return ResponseEntity.ok(moduleService.getModule());
	}
	
	@GetMapping("/getModuleById/{id}")
	public ResponseEntity<Module> getModuleById(@PathVariable String id) {

		return ResponseEntity.ok(moduleService.getModuleById(id));
	}
	
	@PutMapping("/update")
	public ResponseEntity<Module> updateModule(@RequestBody Module module) {

		return ResponseEntity.ok(moduleService.updateModule(module));
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteModule(@PathVariable String id) {

		return ResponseEntity.ok(moduleService.deleteModule(id));
	}

}
