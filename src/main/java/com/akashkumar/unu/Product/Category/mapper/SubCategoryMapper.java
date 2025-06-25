package com.akashkumar.unu.Product.Category.mapper;

import com.akashkumar.unu.Product.Category.Entity.SubCategory;
import com.akashkumar.unu.Product.Category.SubCategory.SubCategoryDto;

public class SubCategoryMapper {

    public static SubCategory toEntity(SubCategoryDto dto) {
        SubCategory entity = new SubCategory();
        entity.setSubCategoryId(dto.getSubCategoryId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setDealerId(dto.getDealerId());
        entity.setSubCategoryName(dto.getSubCategoryName());
        entity.setSubCategoryType(dto.getSubCategoryType());
        entity.setSubCategories(dto.getSubCategories());
        entity.setSubProducts(dto.getSubProducts());
        return entity;
    }

    public static SubCategoryDto toDto(SubCategory entity) {
        SubCategoryDto dto = new SubCategoryDto();
        dto.setSubCategoryId(entity.getSubCategoryId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setDealerId(entity.getDealerId());
        dto.setSubCategoryName(entity.getSubCategoryName());
        dto.setSubCategoryType(entity.getSubCategoryType());
        dto.setSubCategories(entity.getSubCategories());
        dto.setSubProducts(entity.getSubProducts());
        return dto;
    }
}

