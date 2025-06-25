package com.akashkumar.unu.Product.Category.repository;

import com.akashkumar.unu.Product.Category.Entity.Category;
import com.akashkumar.unu.Product.Category.Entity.SubCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends MongoRepository<SubCategory, String> {

}
