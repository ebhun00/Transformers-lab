package com.titan.Transformerslab.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Document(collection = "appConfig")
public class AppConfigs /*extends AuditEntity */{

	
	@Id()
	private String key;
	private String value;

}
