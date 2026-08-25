package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.entity.Company;
import com.smart.entity.Employee;
import com.smart.service.CompanyService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	CompanyService companyService;
	
	@RolesAllowed("SUPER_ADMIN")
	@PostMapping("/create")
	public ResponseEntity<Company> createCompany(@RequestBody Company company){
		
		return ResponseEntity.ok(companyService.createCompany(company));
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<Company> getById(@PathVariable String id){
		
		return ResponseEntity.ok(companyService.getById(id));
	}

}
