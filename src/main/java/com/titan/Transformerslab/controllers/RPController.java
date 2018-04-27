package com.titan.Transformerslab.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.titan.Transformerslab.domain.Order;

@RestController
public class RPController {
	
	@RequestMapping("/route/{orderNumber}")
	public Order sayHello(@PathVariable(name = "orderNumber") String orderNumber) {

		Order order = new Order(orderNumber, "Titan");
		return order;
	}

}
