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
@Document(collection = Urls.CategoryCollection)
public class Category {
    @Id
    private String categoryId;
    private String dealerId;
    private String categoryName;
    private String categoryImageUrl;

    private String selectedCategoryType;
    private String selectedSubCategoryType;

    /*jo category select ho jayegi usi ke base par data ko store kara dunga */
    private List<String> subCategoryIds = new ArrayList<>();
    private List<String> products = new ArrayList<>();
}
