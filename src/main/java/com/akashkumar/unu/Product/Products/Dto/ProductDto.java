package com.akashkumar.unu.Product.Products.Dto;

import com.akashkumar.unu.Product.Products.Product.Images;
import com.akashkumar.unu.Utilities.Urls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String productId;
    private String userId;
    private String dealerId;

    private String categoryId;
    private String subCategoryId;
    private String categoryType;


    private double price;
    private String description;

    private String selectedCategoryType;
    private String selectedSubCategoryType;

    private String mainImageUrl;
    private List<String> imageUrls = new ArrayList<>();
    private List<String> videoUrls = new ArrayList<>();

    private double discount;
    private int totalSold;
    private int totalView;
    private double totalRating;
    private int inBagQuantity;

    private String howToUse;
    private String keyIngredients;
    private String about;
    private String overview;


    boolean stockStatus;
    boolean codAvailable;
    boolean sold;
    private List<String> buyerUserList = new ArrayList<>();
}
