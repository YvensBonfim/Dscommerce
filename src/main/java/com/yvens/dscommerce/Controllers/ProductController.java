package com.yvens.dscommerce.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yvens.dscommerce.DTO.ProductDto;
import com.yvens.dscommerce.Services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(value ="/products")
public class ProductController {
    @Autowired
    private ProductService service;

   @GetMapping(value = "/{id}")
   public ProductDto findByIDto(@PathVariable Long id) {
      return   service.findById(id);
       
   }
   @GetMapping()
   public Page< ProductDto>  findAll(Pageable pageable) {
      return   service.findAll(pageable);
       
   }
   



    

}
