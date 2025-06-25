package com.akashkumar.unu.Product.Products;

import com.akashkumar.unu.Product.Products.Product.Images;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends MongoRepository<Images, Object> {
}
