package com.akashkumar.unu.Product.Category.SubCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveSubCategory {
    private String subCategoryId;
    private String categoryId;
    private String dealerId;
}
