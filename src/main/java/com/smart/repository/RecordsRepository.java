package com.smart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart.dto.RecordFilter;
import com.smart.entity.Records;

@Repository
public interface RecordsRepository  extends JpaRepository<Records, String>{
	
//	@Query("""
//		    SELECT r
//		    FROM Records r
//		    WHERE r.moduleId = :moduleId
//		      AND (:#{#filter.recordIds} IS NULL OR r.id IN :#{#filter.recordIds}
//		      )
//		""")
//		Page<Records> findByModuleId(
//		        @Param("moduleId") String moduleId,
//		        @Param("filter") RecordFilter filter,
//		        Pageable pageable
//		);
	
	
	@Query("""
		    SELECT r
		    FROM Records r
		    WHERE r.moduleId = :moduleId

		      AND (
            :#{#filter.recordIds == null || #filter.recordIds.isEmpty()} = true
            OR r.id IN :#{#filter.recordIds}
          )
 AND (
            :#{#filter.employeeIds == null || #filter.employeeIds.isEmpty()} = true
            OR r.creatorId IN :#{#filter.employeeIds}
          )
		    
		    """)
		Page<Records> findByModuleId(
		        @Param("moduleId") String moduleId,
		        @Param("filter") RecordFilter filter,
		        Pageable pageable
		);
}
