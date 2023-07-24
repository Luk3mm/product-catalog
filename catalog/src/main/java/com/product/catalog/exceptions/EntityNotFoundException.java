package com.product.catalog.exceptions;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException (String msg){
        super(msg);
    }

}
