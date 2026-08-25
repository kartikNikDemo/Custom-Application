package com.smart.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.dto.EmployeeIdAndName;
import com.smart.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	Employee findEmployeeByEmail(String username);
	
	
	List<Employee> findByCompanyId(String companyId); 

	@Query("""
		    SELECT e
		    FROM Employee e
		    WHERE e.companyId = :companyId
		      AND (
		            :query IS NULL
		            OR :query = ''
		            OR LOWER(e.name) LIKE LOWER(CONCAT('%', :query, '%'))
		          )
		""")
		Page<Employee> findEmployees(
		        @Param("companyId") String companyId,
		        @Param("query") String query,
		        Pageable pageable);
	
	@Query("""
		    SELECT new com.smart.dto.EmployeeIdAndName(
		        e.id,
		        e.name
		    )
		    FROM Employee e
		    WHERE e.companyId = :companyId
		""")
		List<EmployeeIdAndName> getByCompanyIdOrderByCreaetdDate(@Param("companyId") String companyId);
	
}
