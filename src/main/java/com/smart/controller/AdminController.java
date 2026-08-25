package com.smart.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
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

import com.smart.entity.Employee;
import com.smart.entity.Student;
import com.smart.entity.User;
import com.smart.repository.EmployeeRepository;
import com.smart.repository.StudentRepository;
import com.smart.repository.UserRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private StudentRepository studentRepository;

	@GetMapping("/getUsers")
	public ResponseEntity<?> getAllUsers() {

		List<User> userList = userRepository.findAll();

		return ResponseEntity.ok(userList);

	}
	

	@GetMapping("/getAllEmployees")
	public ResponseEntity<?> getAllEmployees() {

		List<Employee> userList = employeeRepository.findAll();

		return ResponseEntity.ok(userList);

	}
	
	
	@PostMapping("/createEmployee")
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee){
		employeeRepository.save(employee);
		return ResponseEntity.ok(employee);
	}
	
	
	@GetMapping("/getByEmployeeId/{employeeId}")
	public ResponseEntity<?> getParticularEmployeeId(@PathVariable("employeeId") String employeeId){
		Employee employee=employeeRepository.findById(employeeId).get();
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/updateEmployee")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee){
		employeeRepository.save(employee);
		return ResponseEntity.ok(employee);
	}

	@DeleteMapping("/deleteByEmployeeId/{employeeId}")
	public ResponseEntity<?> getParticularEmployeeIdToDelete(@PathVariable("employeeId") String employeeId){
		
		employeeRepository.deleteById(employeeId);
		return ResponseEntity.ok("Employee Delete Succefully");
	}
	
	
	@PostMapping("/addStudent")
	public ResponseEntity<?> addStudent(@RequestBody Student student){
		studentRepository.save(student);
		return ResponseEntity.ok(student);
	}
	
	@GetMapping("/getAllStudent")
	public ResponseEntity<?> getAllStudet(){
		List<Student> studentList=studentRepository.findAll();
		return ResponseEntity.ok(studentList);
	}
	
	@PutMapping("/updateStudent")
	public ResponseEntity<?> updateStudet(@RequestBody Student student){
		studentRepository.save(student);
		return ResponseEntity.ok(student);
	}	

}
