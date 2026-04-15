package com.example.EasyCheckout.Exception;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message) {
        super(message) ;
    }
}
