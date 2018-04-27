package com.titan.Transformerslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import com.titan.Transformerslab.domain.RoutePlannerStoreShiftInfo;
import com.titan.Transformerslab.xsd.coExport.domains.TXML;

@Component
public class RPResponseMessageService {

	@Autowired
	public RPResponseContentMapper rpResponseContentMapper;
	
	
	@ServiceActivator(inputChannel= "outboundChannel")
	public void receiveMesssage(TXML txml) {
		RoutePlannerStoreShiftInfo routeInfo = rpResponseContentMapper.getRPRouteDetails("Some message");
		System.out.println(routeInfo);
	}

}
