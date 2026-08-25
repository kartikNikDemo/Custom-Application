package com.smart.controller;

import java.io.Serial;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.dto.EmployeeIdAndName;
import com.smart.dto.request.CreateEmployee;
import com.smart.entity.Employee;
import com.smart.entity.User;
import com.smart.repository.EmployeeRepository;
import com.smart.repository.UserRepository;
import com.smart.service.EmployeeService;
import com.smart.service.UserService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	private User user;
	
	@Autowired
	EmployeeService employeeService;

	@ModelAttribute
	public void loadLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


		user = userRepository.getUserByUserName(authentication.getName());
	}


	@PostMapping("/create")
	public ResponseEntity<?> createEmployee(@RequestBody CreateEmployee createEmployee) {
		
		
		User checkExistingUser=userRepository.getUserByUserName(createEmployee.getUsername());
		
		if(checkExistingUser!=null) {
			 throw new RuntimeException("User already exists with username: "
		                + createEmployee.getUsername());
		}
		
		
		createEmployee.setCompanyId(user.getCompanyId());
	
		
		User createUser = userService.createEmployeeUser(createEmployee);

		Employee employee = objectMapper.convertValue(createEmployee, Employee.class);
		employee.setEmail(createEmployee.getUsername());
		employee.setUser(createUser);

		employeeRepository.save(employee);
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/getEmployees")
	public ResponseEntity<Page<Employee>> getAllEmployees(@RequestParam(required = false) String query,
			Pageable pageable) {
		
		Page<Employee> employees = employeeRepository.findEmployees(user.getCompanyId(), query, pageable);

		return ResponseEntity.ok(employees);
	}


	@GetMapping("/getByEmployeeId/{employeeId}")
	public ResponseEntity<?> getParticularEmployeeId(@PathVariable("employeeId") String employeeId) {
		Employee employee = employeeRepository.findById(employeeId).get();
		return ResponseEntity.ok(employee);
	}

	@PutMapping("/updateEmployee")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		return ResponseEntity.ok(employee);
	}

	@DeleteMapping("/delete/{employeeId}")
	public ResponseEntity<?> getParticularEmployeeIdToDelete(@PathVariable("employeeId") String employeeId) {

		employeeRepository.deleteById(employeeId);
		return ResponseEntity.ok("Employee Delete Succefully");
	}
	

	@GetMapping("/getAllEmployeeIdAndName")
	public ResponseEntity<List<EmployeeIdAndName>> getAllEmployeeIdAndName() {
		
		return ResponseEntity.ok(employeeService.getAllEmployeeIdAndName());
	}

}
