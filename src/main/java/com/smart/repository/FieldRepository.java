package com.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entity.ModuleField;

public interface FieldRepository extends JpaRepository<ModuleField, String> {
	
	List<ModuleField> findByModuleIdOrderByDisplayOrder(String moduleId);

}
