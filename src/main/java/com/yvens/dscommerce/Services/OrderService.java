package com.yvens.dscommerce.Services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yvens.dscommerce.DTO.OrderDTO;
import com.yvens.dscommerce.DTO.OrderItemDTO;
import com.yvens.dscommerce.Entities.Order;
import com.yvens.dscommerce.Entities.OrderItem;
import com.yvens.dscommerce.Entities.OrderStatus;
import com.yvens.dscommerce.Entities.Product;
import com.yvens.dscommerce.Entities.User;
import com.yvens.dscommerce.Repositories.OrderItemRepository;
import com.yvens.dscommerce.Repositories.OrderRepository;
import com.yvens.dscommerce.Repositories.ProductRepository;
import com.yvens.dscommerce.Services.Exceptions.ResourceNotFoundException;


@Service
public class OrderService {

    @Autowired
    OrderRepository repository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" Recurso não encontrado"));
        return new OrderDTO(order);

    }
    @Transactional
    public OrderDTO insert(OrderDTO dto) {

        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        order.setClient(user);

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(item);

        }

        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);

    }

}
