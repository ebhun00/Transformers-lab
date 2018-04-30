package com.titan.Transformerslab.domain;

import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonTypeName("DocRouteDetail")
@JsonIgnoreProperties(ignoreUnknown = true)	
public class DocRouteDetail {

	@JsonProperty("ResourceKey")
	private String resourceKey;
	
	@JsonProperty("RouteID")
	private String routeId ;
	
	@JsonIgnore
	String storeNumber ;
	
	@JsonProperty("ProjectedDepartedDate")
	private String planDepartDate_RefF2;
	
	@JsonIgnore
	private String planDepartDate_RefF6;
	
	@JsonProperty("DocStop")
	private List<OrderRouteInfo> docRouteDetail = new ArrayList<OrderRouteInfo>();
}
