package com.yvens.dscommerce.DTO;

import com.yvens.dscommerce.Entities.Category;

public class CategoryDto {

    private Long id;
    private String name;


    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto(Category entity) {
        id = entity.getId();
        name = entity.getName();
    }


    public CategoryDto() {
    }


    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    



    
    

}
