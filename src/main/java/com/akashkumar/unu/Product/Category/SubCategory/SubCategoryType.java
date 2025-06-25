package com.akashkumar.unu.Product.Category.SubCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "SubCategoryTypes")
@Data @AllArgsConstructor @NoArgsConstructor
public class SubCategoryType {
    @Id
    private String subCategoryTypeId;
    private String dealerId;
    private String categoryTypeId;
    private String subCategoryTypeName;
    private String subCategoryType;
    private List<String> productIdList = new ArrayList<>();
}
