package com.titan.Transformerslab.repository;

import java.util.List;

import com.titan.Transformerslab.domain.RPOrderDomain;

public interface OrderRepositoryCustom {

	int updateOrder(String order, String customerName);
	
	void saveOrders(List<RPOrderDomain> orders);
}
