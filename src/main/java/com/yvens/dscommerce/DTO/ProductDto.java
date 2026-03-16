package com.yvens.dscommerce.DTO;

import com.yvens.dscommerce.Entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDto {

    private Long id;

    @Size(min = 3, max = 80, message = "nome precisa ter entre 3 e 80 caracteres")
    @NotBlank(message = "campo requerido")
    private String name;

    @Size(min = 10, message = "nome precisa ter 10 caracteres")
    @NotBlank(message = "campo requerido")
    private String description;

    @Positive(message = "o preço deve ser positivo")
    private Double price;

    private String imgUrl;

   
    public ProductDto() {
    }

    public ProductDto(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDto(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
    }

    // Getters... (mantenha os que você já tem)
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public String getImgUrl() { return imgUrl; }
}