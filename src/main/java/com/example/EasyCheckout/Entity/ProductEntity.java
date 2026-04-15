package com.example.EasyCheckout.Entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Productdetails")
@Data
public class ProductEntity {


    @Id
    private String product_Id ;
    private String productName ;
    private int price ;
    private int stock ;


    }
