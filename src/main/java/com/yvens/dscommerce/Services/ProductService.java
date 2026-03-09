package com.yvens.dscommerce.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yvens.dscommerce.DTO.ProductDto;
import com.yvens.dscommerce.Entities.Product;
import com.yvens.dscommerce.Repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {

        Product product = repository.findById(id).get();
        return new ProductDto(product);

    }
    @Transactional(readOnly = true)
    public Page <ProductDto> findAll(org.springframework.data.domain.Pageable pageable) {

       Page<Product> result = repository.findAll(pageable);
        return result.map(x->new ProductDto(x));

    }

}
