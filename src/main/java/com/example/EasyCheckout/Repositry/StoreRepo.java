package com.example.EasyCheckout.Repositry;

import com.example.EasyCheckout.Entity.StoreEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StoreRepo extends MongoRepository<StoreEntity, String> {
     List<StoreEntity> findAll() ;

     void deleteById(String s);
}
