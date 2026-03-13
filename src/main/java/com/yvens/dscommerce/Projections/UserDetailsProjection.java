package com.yvens.dscommerce.Projections;

public interface UserDetailsProjection {
    
    String getUsername();
    String getPassword();
    Long getRoleId();
    String getAuthority();
}
