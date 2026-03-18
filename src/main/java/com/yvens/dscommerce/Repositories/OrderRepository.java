package com.yvens.dscommerce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yvens.dscommerce.Entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
