package com.titan.Transformerslab.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonTypeName("DocFWImport") 
@JsonIgnoreProperties(ignoreUnknown = true)	
public class RoutePlannerStoreShiftInfo {
	
	@JsonProperty("Request")
	private Request request;
	
	
}
