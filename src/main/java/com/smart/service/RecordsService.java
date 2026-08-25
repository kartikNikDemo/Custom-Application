package com.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smart.dto.RecordFilter;
import com.smart.entity.Records;
import com.smart.entity.User;
import com.smart.helper.service.CurrentUserService;
import com.smart.repository.RecordsRepository;

@Service
public class RecordsService {

	@Autowired
	CurrentUserService currentUserService;

	@Autowired
	RecordsRepository recordsRepository;

	public Records createRecord(Records record) {

		User user = currentUserService.getCurrentUser();

		record.setCompanyId(user.getCompanyId());
		record.setCreatorId(user.getId());
		return recordsRepository.save(record);
	}

	public Page<Records> getRecordsByModuleId(String moduleId, RecordFilter filter, Pageable pageable) {

		return recordsRepository.findByModuleId(moduleId,filter, pageable);
	}

	@SuppressWarnings("deprecation")
	public Records getById(String id) {
		Records record = recordsRepository.getById(id);

		return record;
	}

	public void deleteById(String id) {
		
		recordsRepository.deleteById(id);
	}

}
