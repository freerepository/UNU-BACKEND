package com.akashkumar.unu.ExceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartApiResponse<T> {
    private String message;
    private String userName;
    private int totalPrice;
    private String userId;
    private T carts;
}
