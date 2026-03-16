package com.yvens.dscommerce.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yvens.dscommerce.DTO.UserDto;
import com.yvens.dscommerce.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping(value = "/users")
public class UserController {
   @Autowired
   private UserService service;

  @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
   @GetMapping(value = "/me")
   public ResponseEntity<UserDto> getMe() {
      UserDto dto = service.getMe();
      return ResponseEntity.ok(dto);

   }

}
