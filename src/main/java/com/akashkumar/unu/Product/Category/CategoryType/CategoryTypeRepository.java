package com.akashkumar.unu.Product.Category.CategoryType;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryTypeRepository extends MongoRepository<CategoryType, String> {
    Optional<CategoryType> findByCategoryType(String type);
}
