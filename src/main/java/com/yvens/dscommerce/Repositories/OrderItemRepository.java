package com.yvens.dscommerce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yvens.dscommerce.Entities.OrderItem;
import com.yvens.dscommerce.Entities.OrderItemPK;



public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
