package com.example.EasyCheckout.Repositry;

import com.example.EasyCheckout.Entity.AdminEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepo extends MongoRepository<AdminEntity , ObjectId> {

    boolean existsByEmail(String email ) ;
    Optional<AdminEntity> findByEmail(String email) ;


}
