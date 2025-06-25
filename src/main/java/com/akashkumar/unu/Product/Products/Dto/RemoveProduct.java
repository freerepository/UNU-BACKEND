package com.akashkumar.unu.Product.Products.Dto;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveProduct {
    private String productId;
    private String userId;
    private String dealerId;
}
