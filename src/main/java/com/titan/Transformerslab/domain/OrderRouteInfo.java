package com.titan.Transformerslab.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonTypeName("DocStop")
@JsonIgnoreProperties(ignoreUnknown = true)	
@AllArgsConstructor
public class OrderRouteInfo {
	
	
	@JsonProperty("OrderKey")
	String orderId;
	
	@JsonProperty("PlannedArrivedDate")
	String planArriveDate_RefF4;
	
	@JsonIgnore
	String planArriveDate_RefF7;
	
	@JsonProperty("StopNumber")
	String stopNumber_RefF5;
	
	String routed;
	String cancelled;
	String actionType;
}
