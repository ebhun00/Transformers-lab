package com.titan.Transformerslab.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RPOrderDomain {

		private String van;

		private String routeId;

		private String storeNumber;

		private String planDepartDate_RefF2;


		OrderRouteInfo docRouteDetail;
	}