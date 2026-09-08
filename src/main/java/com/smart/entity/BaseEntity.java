package com.smart.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.smart.config.CustomUserDetail;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime updatedDate;

	@CreatedBy
	@Column(updatable = false)
	private String createdBy;

	@Column(updatable = false)
	private String creatorId;

	@LastModifiedBy
	private String updatedBy;

	private Boolean active = true;

	private Boolean deleted = false;

	@Column(updatable = false)
	private String companyId;

	@PrePersist
	public void setCreatorId() {

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null ||
	        !authentication.isAuthenticated() ||
	        authentication.getPrincipal() instanceof String) {
	        return;
	    }

	    if (authentication.getPrincipal() instanceof CustomUserDetail user) {

	        this.creatorId = user.getUser().getId();
	        this.companyId = user.getUser().getCompanyId();
	    }
	}

}