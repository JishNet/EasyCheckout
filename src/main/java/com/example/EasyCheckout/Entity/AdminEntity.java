package com.example.EasyCheckout.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "AdminEntity")
@Data
 public class AdminEntity {

    @Id
    private ObjectId Id ;
    private String email  ;
    private String password ;


    }

