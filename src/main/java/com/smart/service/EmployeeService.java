package com.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dto.EmployeeIdAndName;
import com.smart.helper.service.CurrentUserService;
import com.smart.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	CurrentUserService currentUserService;
	 @Autowired
	EmployeeRepository employeeRepository;

	public List<EmployeeIdAndName> getAllEmployeeIdAndName() {
		List<EmployeeIdAndName> employeeList=employeeRepository.getByCompanyIdOrderByCreaetdDate(currentUserService.getCurrentUser().getCompanyId());
		return employeeList;
	}

}
