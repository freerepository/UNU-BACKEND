package com.akashkumar.unu.Product.Products;

import com.akashkumar.unu.Product.Products.Product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product ,String> {
}
