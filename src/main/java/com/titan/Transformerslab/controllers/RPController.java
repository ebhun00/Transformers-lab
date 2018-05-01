package com.titan.Transformerslab.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.titan.Transformerslab.domain.Order;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RPController {
	
	@RequestMapping("/route/{orderNumber}")
	public Order sayHello(@PathVariable(name = "orderNumber") String orderNumber) {
		log.info("Get Route info from DB for an order : "+ orderNumber);
		Order order = new Order(orderNumber, "Titan");
		return order;
	}

}
