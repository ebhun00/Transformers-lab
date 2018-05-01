package com.titan.Transformerslab.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import com.titan.Transformerslab.domain.RPDataWrapper;
import com.titan.Transformerslab.domain.RPOrderDomain;
import com.titan.Transformerslab.domain.Request;
import com.titan.Transformerslab.domain.ResourceKey;
import com.titan.Transformerslab.domain.RoutePlannerStoreShiftInfo;
import com.titan.Transformerslab.service.Mappers.RPResponseContentMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RPResponseMessageService {

	@Autowired
	public RPResponseContentMapper rpResponseContentMapper;

	@ServiceActivator(inputChannel = "rp-input-inbound-channel", outputChannel="rp-outbound-channel")
	public RPDataWrapper receiveMesssage(String rpInputString) {
		log.debug("RP input as JSON : {}", rpInputString);
		RoutePlannerStoreShiftInfo routeInfo = rpResponseContentMapper.getRPRouteDetails(rpInputString);
		Map<String, RPOrderDomain> rpOrderInfoMap = mapByOrderId(routeInfo.getRequest());
		
		List<String> storeShiftVanInfo = Arrays.asList(routeInfo.getRequest().getDocRouteDetails().get(0).getResourceKey().split("_"));
		ResourceKey resourceKey = new ResourceKey(storeShiftVanInfo.get(3),storeShiftVanInfo.get(0),
				storeShiftVanInfo.get(2),storeShiftVanInfo.get(1));
		RPDataWrapper wrapper = new RPDataWrapper(resourceKey, rpOrderInfoMap);
		return wrapper;
	}

	private Map<String, RPOrderDomain> mapByOrderId(Request request) {
		Map<String, RPOrderDomain> rpOrderInfoMap = new HashMap<String, RPOrderDomain>();
		request.getDocRouteDetails().forEach(docRouteDetail -> {
			List<String> storeShiftVanInfo = Arrays.asList(docRouteDetail.getResourceKey().split("_"));
			log.info("Preparing route info for : store :{} Shift:{} date :{}  van : {} ", storeShiftVanInfo.get(0), storeShiftVanInfo.get(1), storeShiftVanInfo.get(2), storeShiftVanInfo.get(3));
			RPOrderDomain domain = new RPOrderDomain((storeShiftVanInfo.get(3)+storeShiftVanInfo.get(1)), docRouteDetail.getRouteId(),
					storeShiftVanInfo.get(0), docRouteDetail.getPlanDepartDate_RefF2(), null);
			docRouteDetail.getDocRouteDetail().forEach(orderRouteInfo -> {
				domain.setDocRouteDetail(orderRouteInfo);
				rpOrderInfoMap.put(orderRouteInfo.getOrderId(), domain);

			});
		});
		return rpOrderInfoMap;
	}
}
