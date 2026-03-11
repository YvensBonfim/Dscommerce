package com.yvens.dscommerce.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yvens.dscommerce.Entities.Product;

public interface ProductRepository extends JpaRepository <Product, Long> {


   @Query("SELECT obj FROM Product obj " +
       "WHERE UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))")
Page<Product> searchByName(String name, org.springframework.data.domain.Pageable pageable);
}
