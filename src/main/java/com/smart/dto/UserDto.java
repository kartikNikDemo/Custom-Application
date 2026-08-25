package com.smart.dto;

import java.util.List;

import com.smart.entity.ModuleAccess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	private String jwtToken;
	private String userId;
	private String name;
	private String email;
	private String role;
	private boolean enabled;
	private String timeZone;
	private String expiryDate;
	private String empId;
	private String adminId;
	private String companyId;
	//@JsonIgnore
	private String shiftTime;
	
	private List<ModuleAccess> moduleAccess;
         

	
	
	
	
	

	
    



	
	
	
	
	
	
}
