package com.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entity.MultiFieldValue;


public interface MultiFieldtValueRepository extends JpaRepository<MultiFieldValue, String>{
	
	List<MultiFieldValue> findByModuleFieldIdOrderByDisplayOrder(String id);

}
