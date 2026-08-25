package com.smart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.entity.MultiFieldValue;
import com.smart.helper.service.CurrentUserService;
import com.smart.repository.MultiFieldtValueRepository;

@Service
public class MultiFieldValueService {
	

	@Autowired
	CurrentUserService currentUserService;
	
	@Autowired
	MultiFieldtValueRepository multiFieldtValueRepository;

	public List<MultiFieldValue> create(List<MultiFieldValue> request) {
		
		List<MultiFieldValue> udpateList=new ArrayList<MultiFieldValue>();
		
		for(MultiFieldValue fieldValue:request) {
			
			fieldValue.setCompanyId(currentUserService.getCurrentUser().getCompanyId());
			udpateList.add(fieldValue);
		}
		
		return multiFieldtValueRepository.saveAll(udpateList);
	}

	public MultiFieldValue update(MultiFieldValue request) {
		return multiFieldtValueRepository.save(request);
	}

	public String delete(String id) {
		multiFieldtValueRepository.deleteById(id);
		
		return "Deleted Successfully";
	}

	public List<MultiFieldValue> getByFieldId(String id) {
		
		return multiFieldtValueRepository.findByModuleFieldIdOrderByDisplayOrder(id);
	}

	public List<MultiFieldValue> bulkUpdate(List<MultiFieldValue> request) {
		// TODO Auto-generated method stub
		return multiFieldtValueRepository.saveAll(request);
	}

}
