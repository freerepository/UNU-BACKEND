package com.akashkumar.unu.Product.Products.Mapper;

import com.akashkumar.unu.Product.Products.Dto.ProductDto;
import com.akashkumar.unu.Product.Products.Product.Product;
import org.springframework.stereotype.Component;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getProductId());
        dto.setUserId(product.getUserId());
        dto.setDealerId(product.getDealerId());
        dto.setCategoryId(product.getCategoryId());
        dto.setSubCategoryId(product.getSubCategoryId());
        dto.setCategoryType(product.getCategoryType());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setMainImageUrl(product.getMainImageUrl());
        dto.setImageUrls(product.getImageUrls());
        dto.setVideoUrls(product.getVideoUrls());
        dto.setDiscount(product.getDiscount());
        dto.setTotalSold(product.getTotalSold());
        dto.setTotalView(product.getTotalView());
        dto.setTotalRating(product.getTotalRating());
        dto.setInBagQuantity(product.getInBagQuantity());
        dto.setHowToUse(product.getHowToUse());
        dto.setKeyIngredients(product.getKeyIngredients());
        dto.setAbout(product.getAbout());
        dto.setOverview(product.getOverview());
        dto.setStockStatus(product.isStockStatus());
        dto.setCodAvailable(product.isCodAvailable());
        dto.setSold(product.isSold());
        dto.setBuyerUserList(product.getBuyerUserList());
        dto.setSelectedCategoryType(product.getSelectedCategoryType());
        dto.setSelectedSubCategoryType(product.getSelectedSubCategoryType());
        return dto;
    }

    public static Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setProductId(dto.getProductId());
        product.setUserId(dto.getUserId());
        product.setDealerId(dto.getDealerId());
        product.setCategoryId(dto.getCategoryId());
        product.setSubCategoryId(dto.getSubCategoryId());
        product.setCategoryType(dto.getCategoryType());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setMainImageUrl(dto.getMainImageUrl());
        product.setImageUrls(dto.getImageUrls());
        product.setVideoUrls(dto.getVideoUrls());
        product.setDiscount(dto.getDiscount());
        product.setTotalSold(dto.getTotalSold());
        product.setTotalView(dto.getTotalView());
        product.setTotalRating(dto.getTotalRating());
        product.setInBagQuantity(dto.getInBagQuantity());
        product.setHowToUse(dto.getHowToUse());
        product.setKeyIngredients(dto.getKeyIngredients());
        product.setAbout(dto.getAbout());
        product.setOverview(dto.getOverview());
        product.setStockStatus(dto.isStockStatus());
        product.setCodAvailable(dto.isCodAvailable());
        product.setSold(dto.isSold());
        product.setBuyerUserList(dto.getBuyerUserList());
        product.setSelectedCategoryType(dto.getSelectedCategoryType());
        product.setSelectedSubCategoryType(dto.getSelectedSubCategoryType());
        return product;
    }
}
