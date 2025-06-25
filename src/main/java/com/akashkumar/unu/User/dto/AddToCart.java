package com.akashkumar.unu.User.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCart {
    private String userId;
    private String productId;
    private String dealerId;
}
