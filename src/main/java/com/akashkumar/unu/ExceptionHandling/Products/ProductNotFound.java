package com.akashkumar.unu.ExceptionHandling.Products;

public class ProductNotFound extends RuntimeException{
    public ProductNotFound(String message) {
        super(message);
    }
}
