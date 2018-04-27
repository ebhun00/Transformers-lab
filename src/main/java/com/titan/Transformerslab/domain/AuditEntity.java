package com.titan.Transformerslab.domain;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AuditEntity {

	@CreatedDate
	private Date createdDate;
	@CreatedBy
	private String createdBy;
	@LastModifiedDate
	private Date updatedDate;
	@LastModifiedBy
	private String updatedBy;
	@Version
	private Long version;
}