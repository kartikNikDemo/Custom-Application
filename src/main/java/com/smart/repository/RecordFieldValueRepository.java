package com.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart.dto.EmployeeIdAndName;
import com.smart.dto.RecordIdAndValue;
import com.smart.entity.RecordFieldValue;

import jakarta.transaction.Transactional;

@Repository
public interface RecordFieldValueRepository extends JpaRepository<RecordFieldValue, String> {

	List<RecordFieldValue> findByRecordIdInAndModuleIdAndCompanyId(List<String> recordIds, String moduleId,
			String companyId);

	List<RecordFieldValue> findByRecordIdAndModuleIdAndCompanyId(String recordId, String moduleId, String companyId);

	List<RecordFieldValue> findByRecordIdAndCompanyId(String recordId, String companyId);

	List<RecordFieldValue> findByIdInAndCompanyId(List<String> Ids, String companyId);

	@Transactional
	int deleteByRecordId(String recordId);

	List<RecordFieldValue> findByRecordId(String recordId);

	@Transactional
	@Modifying
	@Query("""
			UPDATE RecordFieldValue r
			SET r.value = :value
			WHERE r.recordId = :recordId
			  AND r.field = :fieldId
			""")
	int updateValueByRecordIdAndFieldId(@Param("value") String value, @Param("recordId") String recordId,
			@Param("fieldId") String fieldId);

	@Query("""
			    SELECT DISTINCT r.recordId
			    FROM RecordFieldValue r
			    WHERE LOWER(r.value) LIKE LOWER(CONCAT('%', :query, '%'))
			""")
	List<String> getRecordsIds(@Param("query") String query);
	
	RecordFieldValue findByRecordIdAndField(String recordId,String fieldId);
	
	@Query("""
		    SELECT new com.smart.dto.RecordIdAndValue(
		        r.recordId,
		        r.value
		    )
		    FROM RecordFieldValue r
		    WHERE r.companyId = :companyId
		      AND r.moduleId = :moduleId
		      AND r.field = :fieldId
		      AND (:creatorId IS NULL OR r.creatorId = :creatorId)
		""")
		List<RecordIdAndValue> getRecordIdAndValue(
		        @Param("companyId") String companyId,
		        @Param("moduleId") String moduleId,
		        @Param("fieldId") String fieldId,
		        @Param("creatorId") String creatorId
		);
	
}
