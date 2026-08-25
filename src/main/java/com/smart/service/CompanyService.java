package com.smart.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.entity.Company;
import com.smart.entity.User;
import com.smart.repository.CompanyRespository;

@Service
public class CompanyService {

	@Autowired
	CompanyRespository companyRespository;

	@Autowired
	UserService userService;

	public Company createCompany(Company createCompany) {

		Company company = companyRespository.save(createCompany);
		User user = userService.saveAdminUser(company);
		company.setUser(user);
		return companyRespository.save(company);
	}

	public Company getById(String id) {
		Optional<Company> company = companyRespository.findById(id);
		return company.get();
	}

}
