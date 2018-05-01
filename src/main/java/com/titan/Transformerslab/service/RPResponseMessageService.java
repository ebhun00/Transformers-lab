package com.titan.Transformerslab.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import com.titan.Transformerslab.domain.DocRouteDetail;
import com.titan.Transformerslab.domain.OrderRouteInfo;
import com.titan.Transformerslab.domain.RPDataWrapper;
import com.titan.Transformerslab.domain.RPOrderDomain;
import com.titan.Transformerslab.domain.Request;
import com.titan.Transformerslab.domain.ResourceKey;
import com.titan.Transformerslab.domain.RoutePlannerStoreShiftInfo;
import com.titan.Transformerslab.repository.OrderRepositoryCustomImpl;
import com.titan.Transformerslab.service.Mappers.RPResponseContentMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RPResponseMessageService {
	
	@Autowired
	private OrderRepositoryCustomImpl orderRepositoryCustomImpl;

	@Autowired
	public RPResponseContentMapper rpResponseContentMapper;

	@ServiceActivator(inputChannel = "rp-input-inbound-channel", outputChannel="rp-outbound-channel")
	public RPDataWrapper receiveMesssage(String rpInputString) {
		log.debug("RP input as JSON : {}", rpInputString);
		RoutePlannerStoreShiftInfo routeInfo = rpResponseContentMapper.getRPRouteDetails(rpInputString);
		
		List<String> storeShiftVanInfo = Arrays.asList(routeInfo.getRequest().getDocRouteDetails().get(0).getResourceKey().split("_"));
		
		ResourceKey resourceKey = new ResourceKey(storeShiftVanInfo.get(0),storeShiftVanInfo.get(2),storeShiftVanInfo.get(1));
		
		Map<String, RPOrderDomain> rpOrderInfoMap = mapByOrderId(routeInfo.getRequest());
		List<RPOrderDomain> ordersRouteInfo = new ArrayList<RPOrderDomain> (rpOrderInfoMap.values());
		orderRepositoryCustomImpl.saveOrders(ordersRouteInfo);
		RPDataWrapper wrapper = new RPDataWrapper(resourceKey, rpOrderInfoMap);
		return wrapper;
	}

	private Map<String, RPOrderDomain> mapByOrderId(Request request ) {
		Map<String, RPOrderDomain> rpOrderInfoMap = new HashMap<String, RPOrderDomain>();
		
		for(DocRouteDetail docRouteDetail : request.getDocRouteDetails() ) {
			List<String> storeShift_VanInfo = Arrays.asList(docRouteDetail.getResourceKey().split("_"));
			log.info("Preparing route info for : store :{} Shift:{} date :{}  van : {} ", storeShift_VanInfo.get(0), storeShift_VanInfo.get(1), storeShift_VanInfo.get(2), storeShift_VanInfo.get(3));
			for(OrderRouteInfo  orderRouteInfo : docRouteDetail.getOrderRouteInfoList()) {
				String orderKey =  orderRouteInfo.getOrderId();
				if(orderKey !=null) {
					String orderId = orderKey.substring(0, orderKey.length() - 4);
					RPOrderDomain domain = new RPOrderDomain((storeShift_VanInfo.get(3)+storeShift_VanInfo.get(1)), docRouteDetail.getRouteId(),
							storeShift_VanInfo.get(0), docRouteDetail.getPlanDepartDate_RefF2(), orderId,
							orderRouteInfo.getPlanArriveDate_RefF4(),String.format("%03d", Integer.valueOf( orderRouteInfo.getStopNumber_RefF5())), storeShift_VanInfo.get(1),storeShift_VanInfo.get(2));
					rpOrderInfoMap.put(orderId, domain);
				}
			}
			
		}
		/*request.getDocRouteDetails().forEach(docRouteDetail -> {
			List<String> storeShift_VanInfo = Arrays.asList(docRouteDetail.getResourceKey().split("_"));
			log.info("Preparing route info for : store :{} Shift:{} date :{}  van : {} ", storeShift_VanInfo.get(0), storeShift_VanInfo.get(1), storeShift_VanInfo.get(2), storeShift_VanInfo.get(3));
			docRouteDetail.getOrderRouteInfoList().forEach(orderRouteInfo -> {
				RPOrderDomain domain = new RPOrderDomain((storeShift_VanInfo.get(3)+storeShift_VanInfo.get(1)), docRouteDetail.getRouteId(),
						storeShift_VanInfo.get(0), docRouteDetail.getPlanDepartDate_RefF2(), orderRouteInfo.getOrderId(),
						orderRouteInfo.getPlanArriveDate_RefF4(),orderRouteInfo.getStopNumber_RefF5(), storeShift_VanInfo.get(1),storeShift_VanInfo.get(2));
				rpOrderInfoMap.put(orderRouteInfo.getOrderId(), domain);
			});
		});*/
		return rpOrderInfoMap;
	}
}
