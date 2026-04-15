package com.example.EasyCheckout.Exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message) ;
    }
}
