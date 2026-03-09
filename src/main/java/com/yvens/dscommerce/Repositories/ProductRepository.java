package com.yvens.dscommerce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yvens.dscommerce.Entities.Product;

public interface ProductRepository extends JpaRepository <Product, Long> {
    
}
