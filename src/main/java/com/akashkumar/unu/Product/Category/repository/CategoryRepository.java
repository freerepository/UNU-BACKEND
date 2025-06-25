package com.akashkumar.unu.Product.Category.repository;

import com.akashkumar.unu.Product.Category.Entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
}
