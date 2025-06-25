package com.akashkumar.unu.ExceptionHandling.Products;

public class OrderNotFound extends RuntimeException{
    public OrderNotFound(String message) {
        super(message);
    }
}
