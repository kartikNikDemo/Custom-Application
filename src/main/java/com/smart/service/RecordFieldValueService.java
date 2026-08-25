package com.smart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smart.dto.CreatePublicRecord;
import com.smart.dto.CreateRecords;
import com.smart.dto.FieldIdAndValue;
import com.smart.dto.RecordFieldValueIdandValue;
import com.smart.dto.RecordFilter;
import com.smart.dto.RecordIdAndValue;
import com.smart.dto.SingleRecordValueUpdate;
import com.smart.dto.UpdateRecord;
import com.smart.dto.request.ModuleFilter;
import com.smart.entity.Module;
import com.smart.entity.ModuleField;
import com.smart.entity.RecordFieldValue;
import com.smart.entity.Records;
import com.smart.helper.service.CurrentUserService;
import com.smart.repository.RecordFieldValueRepository;
import com.smart.repository.RecordsRepository;

@Service
public class RecordFieldValueService {

	@Autowired
	CurrentUserService currentUserService;

	@Autowired
	RecordsService recordsService;

	@Autowired
	FieldService fieldService;

	@Autowired
	ModuleService moduleService;

	@Autowired
	RecordFieldValueRepository recordFieldValueRepository;

	public Map<String, String> createRecord(CreateRecords recordFieldValue) {

		Module module = moduleService.getById(recordFieldValue.getModuleId());

		List<ModuleField> moduleFields = fieldService.findByModuleId(recordFieldValue.getModuleId());

		Map<String, String> fieldNameMap = moduleFields.stream()
				.collect(Collectors.toMap(ModuleField::getId, ModuleField::getFieldKey));

		Records record = new Records();
		record.setModule(module.getName());
		record.setModuleId(module.getId());

		Records createdRecords = recordsService.createRecord(record);

		List<RecordFieldValue> singleRecord = new ArrayList<RecordFieldValue>();

		for (FieldIdAndValue fieldAndValue : recordFieldValue.getFieldAndValue()) {

			RecordFieldValue fieldValueRecord = new RecordFieldValue();

			fieldValueRecord.setRecordId(createdRecords.getId());
		//	fieldValueRecord.setCompanyId(currentUserService.getCurrentUser().getCompanyId());
		//	fieldValueRecord.setCreatorId(currentUserService.getCurrentUser().getId());
			fieldValueRecord.setModule(module.getName());
			fieldValueRecord.setModuleId(module.getId());
			fieldValueRecord.setValue(fieldAndValue.getFieldValue());
			fieldValueRecord.setField(fieldAndValue.getFieldId());
			singleRecord.add(fieldValueRecord);
		}

		List<RecordFieldValue> savedRecords = recordFieldValueRepository.saveAll(singleRecord);

		Map<String, String> fieldValueResponse = new LinkedHashMap<String, String>();

		// Add all fields with null values
		for (ModuleField field : moduleFields) {
			fieldValueResponse.put(field.getFieldKey(), null);
		}

		// Replace null with actual saved values
		for (RecordFieldValue records : savedRecords) {

			String fieldKey = fieldNameMap.get(records.getField());

			if (fieldKey != null) {
				fieldValueResponse.put(fieldKey, records.getValue());
			}
		}

		return fieldValueResponse;
	}

	public ResponseEntity<Object> getDataByModule(String moduleId, String query, ModuleFilter filter,
			Pageable pageable) {

		List<ModuleField> moduleFields = fieldService.findByModuleId(moduleId);

		Map<String, String> fieldNameMap = moduleFields.stream()
				.collect(Collectors.toMap(ModuleField::getId, ModuleField::getFieldKey));

		Page<Records> records = null;
		List<String> recordIds = null;

		if (query == null || query.isEmpty()) {
			RecordFilter filterRecord = new RecordFilter();

			filterRecord.setRecordIds(recordIds);
			filterRecord.setEmployeeIds(filter.getEmployeeIds());
			records = recordsService.getRecordsByModuleId(moduleId, filterRecord, pageable);

			recordIds = records.getContent().stream().map(Records::getId).toList();

		} else {

			recordIds = recordFieldValueRepository.getRecordsIds(query);

			RecordFilter filterRecord = new RecordFilter();

			filterRecord.setRecordIds(recordIds);

			records = recordsService.getRecordsByModuleId(moduleId, filterRecord, pageable);

		}

		List<RecordFieldValue> recordFieldValues = recordFieldValueRepository.findByRecordIdInAndModuleIdAndCompanyId(
				recordIds, moduleId, currentUserService.getCurrentUser().getCompanyId());

		Map<String, List<RecordFieldValue>> groupedValues = recordFieldValues.stream()
				.collect(Collectors.groupingBy(RecordFieldValue::getRecordId));
		List<Map<String, Object>> response = new ArrayList<>();

		for (Records record : records.getContent()) {

			Map<String, Object> row = new LinkedHashMap<>();
			row.put("id", record.getId());
			row.put("createdAt", record.getCreatedDate());
			row.put("companyId", record.getCompanyId());

			// Initialize all fields with null
			for (ModuleField field : moduleFields) {
				row.put(field.getFieldKey(), null);
			}

			// Fill actual values
			List<RecordFieldValue> values = groupedValues.get(record.getId());

			if (values != null && !values.isEmpty()) {
				for (RecordFieldValue value : values) {

					String fieldKey = fieldNameMap.get(value.getField());

					if (fieldKey != null) {
						row.put(fieldKey, value.getValue());
					}
				}
				response.add(row);
			}

		}
		
		

		Page<Map<String, Object>> pageResponse = new PageImpl<>(response, pageable, records.getTotalElements());

		return ResponseEntity.ok(pageResponse);
	}

	public Map<String, Object> getModuleDataByRecordId(String moduleId, String recordId) {

		List<ModuleField> moduleFields = fieldService.findByModuleId(moduleId);

		Records record = recordsService.getById(recordId);

		Map<String, String> fieldNameMap = moduleFields.stream()
				.collect(Collectors.toMap(ModuleField::getId, ModuleField::getFieldKey));

		List<RecordFieldValue> recordFieldValues = recordFieldValueRepository.findByRecordIdAndModuleIdAndCompanyId(
				recordId, moduleId, currentUserService.getCurrentUser().getCompanyId());

		Map<String, Object> fieldValueResponse = new LinkedHashMap<String, Object>();

		// Add all fields with null values
		for (ModuleField field : moduleFields) {
			fieldValueResponse.put("id", record.getId());
			fieldValueResponse.put("createdAt", record.getCreatedDate());
			fieldValueResponse.put("companyId", record.getCompanyId());
			fieldValueResponse.put(field.getFieldKey(), null);
		}

		// Replace null with actual saved values
		for (RecordFieldValue records : recordFieldValues) {

			String fieldKey = fieldNameMap.get(records.getField());

			if (fieldKey != null) {
				fieldValueResponse.put(fieldKey, records.getValue());
			}
		}

		return fieldValueResponse;
	}

	public List<RecordFieldValue> getFieldIdByRecordId(String recordId) {
		List<RecordFieldValue> records = recordFieldValueRepository.findByRecordId(recordId);
		return records;
	}

	public Map<String, Object> bulkUpdateRecordFieldValue(UpdateRecord updateRecord) {
		Module module = moduleService.getById(updateRecord.getModuleId());
		Records recordData = recordsService.getById(updateRecord.getRecordId());
		Map<String, Object> fieldValueResponse = new LinkedHashMap<String, Object>();

		List<RecordFieldValueIdandValue> fieldIdAndValue = updateRecord.getRecordFieldValueIdandValue();

		List<ModuleField> moduleFields = fieldService.findByModuleId(updateRecord.getModuleId());
		Map<String, String> fieldNameMap = moduleFields.stream()
				.collect(Collectors.toMap(ModuleField::getId, ModuleField::getFieldKey));

		String companyId = currentUserService.getCurrentUser().getCompanyId();

		List<String> recordFieldValueIds = new ArrayList<String>();

		for (RecordFieldValueIdandValue records : fieldIdAndValue) {

			recordFieldValueIds.add(records.getRecordFieldValueId());
		}

		List<RecordFieldValue> records = recordFieldValueRepository.findByIdInAndCompanyId(recordFieldValueIds,
				companyId);

		List<RecordFieldValue> updateRecordFieldValue = new ArrayList<RecordFieldValue>();

		List<FieldIdAndValue> newValues = new ArrayList<FieldIdAndValue>();

		for (RecordFieldValueIdandValue data : fieldIdAndValue) {

			for (RecordFieldValue record : records) {
				if (data.getRecordFieldValueId() != null && !data.getRecordFieldValueId().isEmpty()) {
					if (data.getRecordFieldValueId().equals(record.getId())) {

						// Update the value
						record.setValue(data.getFieldValue());

						updateRecordFieldValue.add(record);

						break;
					}
				} else {
					FieldIdAndValue newValue = new FieldIdAndValue();
					newValue.setFieldId(data.getFieldId());
					newValue.setFieldValue(data.getFieldValue());
					newValues.add(newValue);
				}
			}
		}

		List<RecordFieldValue> updateRecords = recordFieldValueRepository.saveAll(updateRecordFieldValue);

		// Add all fields with null values
		for (ModuleField field : moduleFields) {
			fieldValueResponse.put(field.getFieldKey(), null);
		}

		// Replace null with actual saved values
		for (RecordFieldValue updateData : updateRecords) {

			String fieldKey = fieldNameMap.get(updateData.getField());

			if (fieldKey != null) {

				fieldValueResponse.put(fieldKey, updateData.getValue());
			}
		}

		CreateRecords newRecord = new CreateRecords();

		newRecord.setModuleId(updateRecord.getModuleId());
		newRecord.setFieldAndValue(newValues);

		List<RecordFieldValue> singleRecord = new ArrayList<RecordFieldValue>();

		for (FieldIdAndValue fieldAndValue : newRecord.getFieldAndValue()) {

			RecordFieldValue fieldValueRecord = new RecordFieldValue();

			fieldValueRecord.setRecordId(updateRecord.getRecordId());
			fieldValueRecord.setCompanyId(currentUserService.getCurrentUser().getCompanyId());
			fieldValueRecord.setCreatorId(currentUserService.getCurrentUser().getId());
			fieldValueRecord.setModule(module.getName());
			fieldValueRecord.setModuleId(module.getId());
			fieldValueRecord.setValue(fieldAndValue.getFieldValue());
			fieldValueRecord.setField(fieldAndValue.getFieldId());
			singleRecord.add(fieldValueRecord);
		}

		List<RecordFieldValue> savedRecords = recordFieldValueRepository.saveAll(singleRecord);

		Map<String, Object> newfieldValueResponse = new LinkedHashMap<String, Object>();

		for (RecordFieldValue newRecords : savedRecords) {

			String fieldKey = fieldNameMap.get(newRecords.getField());

			if (fieldKey != null) {

				newfieldValueResponse.put(fieldKey, newRecords.getValue());
			}
		}

		

		for (Map.Entry<String, Object> entry : newfieldValueResponse.entrySet()) {
			if (entry.getValue() != null) {
				fieldValueResponse.put(entry.getKey(), entry.getValue());
			}
		}

		fieldValueResponse.put("id", recordData.getId());
		fieldValueResponse.put("createdAt", recordData.getCreatedDate());
		fieldValueResponse.put("companyId", recordData.getCompanyId());

		

		return fieldValueResponse;
	}

	public List<RecordFieldValueIdandValue> getRecordDataForEdit(String recordId) {

		List<RecordFieldValue> records = recordFieldValueRepository.findByRecordIdAndCompanyId(recordId,
				currentUserService.getCurrentUser().getCompanyId());
		List<RecordFieldValueIdandValue> response = new ArrayList<RecordFieldValueIdandValue>();

		for (RecordFieldValue record : records) {

			RecordFieldValueIdandValue data = new RecordFieldValueIdandValue();
			data.setFieldId(record.getField());
			data.setFieldValue(record.getValue());
			data.setRecordFieldValueId(record.getId());

			response.add(data);

		}

		return response;
	}

	public String updaetSingleRecordValue(SingleRecordValueUpdate update) {

		List<ModuleField> mdouelFields = fieldService.findByModuleId(update.getModuleId());

		String fieldId = null;

		for (ModuleField field : mdouelFields) {

			if (update.getModuleFieldKey().equals(field.getFieldKey())) {

				fieldId = field.getId();
			}
		}
		
		System.err.println(fieldId);
		System.err.println("recordID "+update.getRecordId());
		System.err.println("newValue "+update.getNewValue());
		
	  RecordFieldValue recordFieldValue=recordFieldValueRepository.findByRecordIdAndField( update.getRecordId(), fieldId);
		
		if(recordFieldValue==null) {
			
			Module moduleData=moduleService.getById(update.getModuleId());
			
			RecordFieldValue fieldValueRecord = new RecordFieldValue();

			fieldValueRecord.setRecordId(update.getRecordId());
		//	fieldValueRecord.setCompanyId(currentUserService.getCurrentUser().getCompanyId());
		//	fieldValueRecord.setCreatorId(currentUserService.getCurrentUser().getId());
			fieldValueRecord.setModule(moduleData.getId());
			fieldValueRecord.setModuleId(update.getModuleId());
			fieldValueRecord.setValue(update.getNewValue());
			fieldValueRecord.setField(fieldId);
			
			recordFieldValueRepository.save(fieldValueRecord);
			
			return null;
			
		}

		recordFieldValueRepository.updateValueByRecordIdAndFieldId(update.getNewValue(), update.getRecordId(), fieldId);

		return null;
	}
	
	
	
	public Map<String, String> createPublicRecord(String companyId,CreatePublicRecord recordFieldValue) {

		Module module = moduleService.getById(recordFieldValue.getModuleId());

		List<ModuleField> moduleFields = fieldService.findByModuleId(recordFieldValue.getModuleId());

		Map<String, String> fieldNameMap = moduleFields.stream()
				.collect(Collectors.toMap(ModuleField::getId, ModuleField::getFieldKey));

		Records record = new Records();
		record.setModule(module.getName());
		record.setModuleId(module.getId());

		Records createdRecords = recordsService.createRecord(record);

		List<RecordFieldValue> singleRecord = new ArrayList<RecordFieldValue>();

		for (FieldIdAndValue fieldAndValue : recordFieldValue.getFieldAndValue()) {

			RecordFieldValue fieldValueRecord = new RecordFieldValue();

			fieldValueRecord.setRecordId(createdRecords.getId());
			fieldValueRecord.setCompanyId(companyId);
		//	fieldValueRecord.setCreatorId(currentUserService.getCurrentUser().getId());
			fieldValueRecord.setModule(module.getName());
			fieldValueRecord.setModuleId(module.getId());
			fieldValueRecord.setValue(fieldAndValue.getFieldValue());
			fieldValueRecord.setField(fieldAndValue.getFieldId());
			singleRecord.add(fieldValueRecord);
		}

		List<RecordFieldValue> savedRecords = recordFieldValueRepository.saveAll(singleRecord);

		Map<String, String> fieldValueResponse = new LinkedHashMap<String, String>();

		// Add all fields with null values
		for (ModuleField field : moduleFields) {
			fieldValueResponse.put(field.getFieldKey(), null);
		}

		// Replace null with actual saved values
		for (RecordFieldValue records : savedRecords) {

			String fieldKey = fieldNameMap.get(records.getField());

			if (fieldKey != null) {
				fieldValueResponse.put(fieldKey, records.getValue());
			}
		}

		return fieldValueResponse;
	}

	public String deleteByRecordId(String recordId) {
		
		recordsService.deleteById(recordId);
		
		recordFieldValueRepository.deleteByRecordId(recordId);
		
		return "Delete Successfully";
	}

	public List<RecordIdAndValue> getRecordIdAndValue(String moduleId, String fieldId) {
		 
		 String creatorId=null;
		
		 List<RecordIdAndValue> records=recordFieldValueRepository.getRecordIdAndValue(currentUserService.getCurrentUser().getCompanyId(),moduleId,fieldId,creatorId);
;		return records;
	}

}
