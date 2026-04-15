package com.example.EasyCheckout.Exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message){
        super(message) ;
    }

}
