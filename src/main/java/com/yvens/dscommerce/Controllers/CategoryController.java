package com.yvens.dscommerce.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yvens.dscommerce.DTO.CategoryDto;
import com.yvens.dscommerce.Services.CategoryService;



@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAll() {
        List<CategoryDto> list = service.findAll();
        return ResponseEntity.ok(list);
    }
}
