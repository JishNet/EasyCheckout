package com.example.EasyCheckout.Controller;

import com.example.EasyCheckout.Entity.BillEntity;
import com.example.EasyCheckout.Service.PaymentService;
import com.example.EasyCheckout.dto.BillEntityResquest;
import com.example.EasyCheckout.dto.BillRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;


@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // 🔥 CREATE BILL
    @PostMapping("/create-bill")
    public Map<String, Object> createOrder(@RequestBody BillEntityResquest data) throws Exception {

        BillEntity bill = new BillEntity();

        bill.setStoreID(data.getStoreID());
        bill.setCustomerphone(data.getCustomerphone());
        bill.setItems(data.getItems());

        bill.setTotalprice(data.getTotal());
        bill.setCreatedAt(LocalDateTime.now());
        bill.setPaymentStatus("CREATED");

        return paymentService.createOrderAndSave(bill);
    }
    // 🔥 VERIFY PAYMENT
    @PostMapping("/verify")
    public String verifyPayment(@RequestBody Map<String, String> data) {

        System.out.println("VERIFY REQUEST => " + data);

        String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String signature = data.get("razorpay_signature");

        return paymentService.verifyAndUpdatePayment(orderId, paymentId, signature);
    }
}