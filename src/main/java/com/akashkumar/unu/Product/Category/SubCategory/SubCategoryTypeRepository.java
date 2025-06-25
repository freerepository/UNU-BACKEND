package com.akashkumar.unu.Product.Category.SubCategory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoryTypeRepository extends MongoRepository<SubCategoryType, String> {
    Optional<SubCategoryType> findBySubCategoryType(String type);
}
