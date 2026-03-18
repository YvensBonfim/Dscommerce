package com.yvens.dscommerce.Services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yvens.dscommerce.DTO.CategoryDto;
import com.yvens.dscommerce.Entities.Category;
import com.yvens.dscommerce.Repositories.CategoryRepository;



@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

  @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        List<Category> result = repository.findAll();
        result.stream().map(x->new CategoryDto(x)).toList();
            return null;
    }
}
