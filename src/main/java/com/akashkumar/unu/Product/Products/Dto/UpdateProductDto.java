package com.akashkumar.unu.Product.Products.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto {
    private String productId;
    private String dealerId;

    private double price;
    private String description;

    private double discount;

    private String howToUse;
    private String keyIngredients;
    private String about;
    private String overview;

    boolean stockStatus;
    boolean codAvailable;
    boolean sold;
}
