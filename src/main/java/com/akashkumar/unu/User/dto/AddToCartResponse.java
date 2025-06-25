package com.akashkumar.unu.User.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddToCartResponse <T>{
    private String status;
    private double totalPrice;
    private int totalProducts;
    private T products;
}
