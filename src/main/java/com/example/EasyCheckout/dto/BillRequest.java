package com.example.EasyCheckout.dto;

import com.example.EasyCheckout.Entity.BillItems;
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
