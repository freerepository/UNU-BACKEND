package com.akashkumar.unu.Product.Category.SubCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubCategoryType {
    private String subCategoryTypeId;
    private String dealerId;
    private String categoryTypeId;
    private String subCategoryTypeName;
    private String subCategoryType;
}
