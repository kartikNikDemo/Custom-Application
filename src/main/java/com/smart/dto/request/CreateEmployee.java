package com.smart.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEmployee {
	private String Name;
	private String secondName;
	private String work;
	private String email;
	private String phone;
	private String image;
	@Column(length = 5000)
	private String description;
	private String department;
	private String shiftTime;
	private String Gender;
	private String Hiredate;
	private String username;
	private String password;
	private String companyId;
}
