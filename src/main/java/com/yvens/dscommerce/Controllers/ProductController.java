package com.yvens.dscommerce.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.yvens.dscommerce.DTO.ProductDto;

import com.yvens.dscommerce.Services.ProductService;

import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
   @Autowired
   private ProductService service;

  @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
   @GetMapping(value = "/{id}")
   public ResponseEntity<ProductDto> findByID(@PathVariable Long id) {
      ProductDto dto = service.findById(id);
      return ResponseEntity.ok(dto);

   }

   @GetMapping()
   public ResponseEntity<Page<ProductDto>> findAll(@RequestParam(name = "name", defaultValue = "") String name,Pageable pageable) {
      Page<ProductDto> dto = service.findAll(name,pageable);
      return ResponseEntity.ok(dto);

   }

  @PreAuthorize("hasRole('ADMIN')")
   @PostMapping()
   public ResponseEntity<ProductDto> insert(@Valid @RequestBody ProductDto dto) {
      dto = service.Insert(dto);
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
      return ResponseEntity.created(uri).body(dto);

   }

   @PutMapping(value = "/{id}")
   public ResponseEntity<ProductDto> update(@Valid @PathVariable Long id,@Valid @RequestBody ProductDto dto) {
      dto = service.Update(id, dto);
      return ResponseEntity.ok(dto);

   }

   @DeleteMapping(value = "/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      service.delete(id);
      return ResponseEntity.noContent().build();

   }
}
