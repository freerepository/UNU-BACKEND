package com.akashkumar.unu.Product.Category.mapper;

import com.akashkumar.unu.Product.Category.Entity.Category;
import com.akashkumar.unu.Product.Category.dto.CategoryDto;

public class CategoryMapper {

    public static Category toEntity(CategoryDto dto) {
        Category category = new Category();
        category.setCategoryId(dto.getCategoryId());
        category.setDealerId(dto.getDealerId());
        category.setCategoryName(dto.getCategoryName());
        category.setCategoryImageUrl(dto.getCategoryImageUrl());
        category.setProducts(dto.getProducts());
        category.setSubCategoryIds(dto.getSubCategoryIds());
        category.setSelectedCategoryType(dto.getSelectedCategoryType());
        category.setSelectedSubCategoryType(dto.getSelectedSubCategoryType());
        return category;
    }

    public static CategoryDto toDto(Category entity) {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(entity.getCategoryId());
        dto.setDealerId(entity.getDealerId());
        dto.setCategoryName(entity.getCategoryName());
        dto.setCategoryImageUrl(entity.getCategoryImageUrl());
        dto.setProducts(entity.getProducts());
        dto.setSubCategoryIds(entity.getSubCategoryIds());
        dto.setSelectedCategoryType(entity.getSelectedCategoryType());
        dto.setSelectedSubCategoryType(entity.getSelectedSubCategoryType());
        return dto;
    }
}

