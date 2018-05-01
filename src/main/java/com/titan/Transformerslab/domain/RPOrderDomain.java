package com.titan.Transformerslab.domain;


import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Document(collection="orderRouteInfo")
public class RPOrderDomain {

		private String van;

		private String routeId;

		private String storeNumber;

		private String planDepartDate_RefF2;

		private String orderId;
		
		private String planArriveDate_RefF4;
		
		private String stopNumber_RefF5;
		
		private String shiftNumber;
		
		private String orderDeliveryDate;
	}