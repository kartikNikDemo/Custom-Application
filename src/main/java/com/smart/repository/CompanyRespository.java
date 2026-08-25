package com.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.entity.Company;

@Repository
public interface CompanyRespository  extends JpaRepository<Company, String>{

}
