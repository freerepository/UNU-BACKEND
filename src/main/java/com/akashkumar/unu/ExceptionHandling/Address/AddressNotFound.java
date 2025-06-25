package com.akashkumar.unu.ExceptionHandling.Address;

public class AddressNotFound extends RuntimeException{
    public AddressNotFound(String message) {
        super(message);
    }
}
