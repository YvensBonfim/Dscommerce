package com.yvens.dscommerce.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yvens.dscommerce.DTO.CategoryDto;
import com.yvens.dscommerce.DTO.ProductDto;
import com.yvens.dscommerce.DTO.ProductMinDto;
import com.yvens.dscommerce.Entities.Category;
import com.yvens.dscommerce.Entities.Product;
import com.yvens.dscommerce.Repositories.ProductRepository;
import com.yvens.dscommerce.Services.Exceptions.DatabaseException;
import com.yvens.dscommerce.Services.Exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" Recurso não encontrado"));
        return new ProductDto(product);

    }

    @Transactional(readOnly = true)
    public Page<ProductMinDto> findAll(String name, Pageable pageable) {

        Page<Product> result = repository.searchByName(name, pageable);
        return result.map(x -> new ProductMinDto(x));

    }

    @Transactional
    public ProductDto Insert(ProductDto productDto) {

        Product entity = new Product();
        copyDtoToEntity(productDto, entity);

        entity = repository.save(entity);
        return new ProductDto(entity);

    }

    @Transactional
    public ProductDto Update(Long id, ProductDto productDto) {
        try {
            Product entity = repository.getReferenceById(id);
            copyDtoToEntity(productDto, entity);

            entity = repository.save(entity);
            return new ProductDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }

    }

    private void copyDtoToEntity(ProductDto productDto, Product entity) {
        entity.setName(productDto.getName());
        entity.setDescription(productDto.getDescription());
        entity.setPrice(productDto.getPrice());
        entity.setImgUrl(productDto.getImgUrl());

        entity.getCategories().clear();

        for (CategoryDto catDto : productDto.getCategories()) {
            Category cat = new Category();
            cat.setId(catDto.getId());
            entity.getCategories().add(cat);

        }

    }

}
