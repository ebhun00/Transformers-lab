package com.titan.Transformerslab.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ShiftOrderDetails {
	
	String orderKey;
	String routeId;
	String stopNumber;
	String stopId;
}
