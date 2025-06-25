package com.akashkumar.unu.Product.Category.CategoryType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryType {
    private String categoryTypeId;
    private String dealerId;
    private String categoryType;
}
