package com.akashkumar.unu.Product.Category.Entity;

import com.akashkumar.unu.Utilities.Urls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "SubCategory")
public class SubCategory {
    @Id
    private String subCategoryId;
    private String categoryId;
    private String dealerId;

    private String subCategoryName;
    private String subCategoryType;
    private List<String> subCategories = new ArrayList<>();
    private List<String> subProducts = new ArrayList<>();

}
