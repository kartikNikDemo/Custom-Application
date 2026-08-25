package com.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entity.ModuleAccess;

public interface ModuleAccessRepository extends JpaRepository<ModuleAccess, String>{

	List<ModuleAccess> findByCompanyIdAndUserId(String companyId ,String userId);
	
	ModuleAccess findByCompanyIdAndModuleIdAndUserId(String companyId ,String moduleId, String userId);
}
