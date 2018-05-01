package com.titan.Transformerslab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.titan.Transformerslab.domain.Order;
import com.titan.Transformerslab.repository.EOMRepository;
import com.titan.Transformerslab.repository.OrderRepositoryCustomImpl;
import com.titan.Transformerslab.service.OrderService;
import com.titan.Transformerslab.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OrderController {

	@Autowired
	private OrderRepositoryCustomImpl orderRepositoryCustomImpl;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	public EOMRepository eomRepository;
	
	@RequestMapping("/order/{orderNumber}")
	public Order save(@PathVariable(name = "orderNumber") String orderNumber) {
		log.info("Fetching order details for order "+ orderNumber);
		Order order = new Order(orderNumber, "Titan");
		orderRepositoryCustomImpl.saveOrder(order);
		return order;
	}

	@PutMapping("/order/{orderNumber}")
	public Order updateOrder(@PathVariable(name = "orderNumber") String orderNumber) {
		Order order = new Order(orderNumber, "Titan");
		orderRepositoryCustomImpl.updateOrder(orderNumber, "app_user");
		return order;
	}
	
	@RequestMapping("/order-details/{orderNumber}")
	public void getOrderDetails(@PathVariable(name = "orderNumber") String orderNumber) {
		log.info("Fetching order details for order from EOM "+ orderNumber);
		orderService.getOrderDetails(orderNumber);
	}
	
	@RequestMapping("/order-details/{storeNumber}/shift/{shiftNumber}")
	public void getEOMStoreShifOrders(@PathVariable(name = "storeNumber") String storeNumber, 
			@PathVariable(name = "shiftNumber") String shiftNumber) throws Exception {
		eomRepository.getShiftOrders(storeNumber,shiftNumber, DateUtils.getCurrentDateIn_YYYYMMDD());
	}

}
