package com.akashkumar.unu.Product.Category.CategoryType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "CategoryTypes")
@Data @AllArgsConstructor @NoArgsConstructor
public class CategoryType {
    @Id
    private String categoryTypeId;
    private String dealerId;
    private String categoryName;
    private String categoryType;
    private List<String> listSubCategory = new ArrayList<>();
    private List<String> productIdList = new ArrayList<>();
}
