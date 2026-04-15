package com.example.EasyCheckout.Exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String message) {
        super(message) ;

    }

}
