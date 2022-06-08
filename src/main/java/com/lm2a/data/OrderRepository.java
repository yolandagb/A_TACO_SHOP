package com.lm2a.data;

import org.springframework.data.repository.CrudRepository;

import com.lm2a.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
