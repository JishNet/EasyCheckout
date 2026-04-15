package com.example.EasyCheckout.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Stores")
@Data
public class StoreEntity {
    @Id
     private  String storeID ;
     private String storename ;
     private String storeupiId ;
     private Long phone ;

}
