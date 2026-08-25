package com.smart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	    @Index(name = "companyId", columnList = "companyId")
	})
public class User extends BaseEntity {

	private String name;
	@Column(unique = true)
	private String username;
	@JsonIgnore
	private String password;

	private String role;
	private boolean enabled;

	private String expiryDate;

	private String timeZone;
	
	private String companyId;

}
