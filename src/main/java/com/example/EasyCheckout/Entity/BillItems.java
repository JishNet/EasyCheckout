package com.example.EasyCheckout.Entity;

import lombok.Data;

@Data
public class BillItems {
    private String id;
    private String name;
    private int quantity;
    private double price;
}