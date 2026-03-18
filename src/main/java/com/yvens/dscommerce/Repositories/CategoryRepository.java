package com.yvens.dscommerce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yvens.dscommerce.Entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
