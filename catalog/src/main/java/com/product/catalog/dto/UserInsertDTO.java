package com.product.catalog.dto;

import com.product.catalog.services.validation.UserInsertValid;
@UserInsertValid
public class UserInsertDTO extends UserDTO {
    private String password;

    UserInsertDTO(){
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
