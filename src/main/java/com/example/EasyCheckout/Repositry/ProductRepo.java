package com.example.EasyCheckout.Repositry;

import com.example.EasyCheckout.Entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepo extends MongoRepository<ProductEntity, String> {
    @Override
    Optional<ProductEntity> findById(String s);
}

