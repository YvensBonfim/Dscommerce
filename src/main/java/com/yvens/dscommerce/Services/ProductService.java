package com.yvens.dscommerce.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page <ProductDto> findAll(Pageable pageable) {

       Page<Product> result = repository.findAll(pageable);
        return result.map(x->new ProductDto(x));

    }
    @Transactional
    public  ProductDto Insert(ProductDto productDto) {

        Product entity = new Product();
        entity.setName(productDto.getName());
        entity.setDescription(productDto.getDescription());
        entity.setPrice(productDto.getPrice());
        entity.setImgUrl(productDto.getImgUrl());

        entity = repository.save(entity);
        return new ProductDto(entity);

    }

}
