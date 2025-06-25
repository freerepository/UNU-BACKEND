package com.akashkumar.unu.Product.Products.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "images")
public class Images {
    @Id
    private String imageId;
    private String categoryId;
    private String subCategoryId;
    private String productId;
    private String imageUrl;
}
