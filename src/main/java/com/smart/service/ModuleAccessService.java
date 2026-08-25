package com.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.entity.ModuleAccess;
import com.smart.helper.service.CurrentUserService;
import com.smart.repository.ModuleAccessRepository;

@Service
public class ModuleAccessService {
	
	@Autowired
	private CurrentUserService currentUserService;
	
	@Autowired
	ModuleAccessRepository moduleAccessRepository;

	public ModuleAccess createModuleAccess(ModuleAccess moduleAccess) {
		
		return moduleAccessRepository.save(moduleAccess);
	}
	

	public ModuleAccess updateModuleAccess(ModuleAccess moduleAccess) {
		     
	ModuleAccess saved=  	 moduleAccessRepository.save(moduleAccess);
		
		return getById(saved.getId());
	}


	public List<ModuleAccess> getByUserId(String userId) {

		return moduleAccessRepository.findByCompanyIdAndUserId(currentUserService.getCurrentUser().getCompanyId(),userId);
	}


	public ModuleAccess getByModuleIdAndUserId(String moduleId, String userId) {
		
		return moduleAccessRepository.findByCompanyIdAndModuleIdAndUserId(currentUserService.getCurrentUser().getCompanyId(), moduleId, userId);
	}
	 
	

	public ModuleAccess getById(String id) {

		return moduleAccessRepository.findById(id).get();
	}
	
	public List<ModuleAccess> getByUserIdAndCompanyId(String userId,String companyId) {

		return moduleAccessRepository.findByCompanyIdAndUserId(companyId,userId);
	}
	

}
