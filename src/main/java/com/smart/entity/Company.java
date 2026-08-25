package com.smart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Company extends BaseEntity {
	
	private String name;
	private String city;
	private String state;
	private String country;
	private String pincode;
	private String address;
	private String gstin;
	private String contactNumber;
	private String email;
	@NotBlank(message = "Company login email is required")
	@Column(unique = true)
	private String loginEmail;
    
    
    @ManyToOne
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
