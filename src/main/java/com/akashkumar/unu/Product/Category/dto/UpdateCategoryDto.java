package com.akashkumar.unu.Product.Category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryDto {
    private String categoryId;
    private String dealerId;
    private String categoryName;
}
