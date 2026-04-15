package com.example.EasyCheckout.Entity;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Billitems")
@Data
public class BillItems {

    private String productId ;
    private int itemQuantity ;

}
