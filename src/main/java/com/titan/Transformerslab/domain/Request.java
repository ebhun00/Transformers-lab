package com.titan.Transformerslab.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonTypeName("Request") 
@JsonIgnoreProperties(ignoreUnknown = true)	
public class Request {

	@JsonProperty("DocRouteDetail")
	private List<DocRouteDetail> docRouteDetails = new ArrayList<DocRouteDetail>();

}
