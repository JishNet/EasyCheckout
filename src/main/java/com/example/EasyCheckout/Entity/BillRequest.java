package com.example.EasyCheckout.Entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class BillRequest {

    private String storeID ;
    private Long userphone ;
    private List<BillItems> billitem ;

}
