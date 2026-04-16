
package com.example.EasyCheckout.dto;

import com.example.EasyCheckout.Entity.BillItems;
import lombok.Data;

import java.util.List;

@Data
public class BillEntityResquest {

    private String storeID;
    private Long customerphone;
    private List<BillItems> items;
    private double total;

}