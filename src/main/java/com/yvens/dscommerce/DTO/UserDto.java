package com.yvens.dscommerce.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.yvens.dscommerce.Entities.User;

public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;
   

    private List<String> roles =new ArrayList<>();

    public UserDto(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        phone = entity.getPhone();
        birthDate = entity.getBirthDate();
     
        for(GrantedAuthority role : entity.getRoles()){
            roles.add(role.getAuthority());
            
        }
       
    }

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }


    public List<String> getRoles() {
        return roles;
    }

    

    

    

}
