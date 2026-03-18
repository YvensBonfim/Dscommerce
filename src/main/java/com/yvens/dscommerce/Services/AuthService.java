package com.yvens.dscommerce.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yvens.dscommerce.Entities.User;
import com.yvens.dscommerce.Services.Exceptions.Forbiden.ForbidenException;

@Service
public class AuthService {


    @Autowired
    private UserService userService;
    public void validateSelfOrAdmin(long usedId){
        User me =userService.authenticated();
        if (!me.hasRole("ROLE_ADMIN")&&!me.getId().equals(usedId)) {

            throw new ForbidenException("Acess denied");
            
        }
    }
    

}
