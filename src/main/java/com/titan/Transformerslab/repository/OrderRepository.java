package com.titan.Transformerslab.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.titan.Transformerslab.domain.Order;

public interface OrderRepository extends MongoRepository<Order, Long> {

	Order findFirstByOrderNumber(String order);

	Order findByOrderNumber(String order, String customerName);

	// Supports native JSON query string
	@Query("{domain:'?0'}")
	Order findCustomByOrderNumber(String order);

	@Query("{domain: { $regex: ?0 } })")
	List<Order> findCustomByRegExOrderNumber(String order);

}