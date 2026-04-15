package com.example.EasyCheckout.Repositry;

import com.example.EasyCheckout.Entity.BillEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillRepo extends MongoRepository<BillEntity, String> {
    BillEntity findByRazorpayOrderId(String orderId);
}
