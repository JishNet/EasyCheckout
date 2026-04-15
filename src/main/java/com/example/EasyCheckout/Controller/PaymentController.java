package com.example.EasyCheckout.Controller;

import com.example.EasyCheckout.Entity.BillEntity;
import com.example.EasyCheckout.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // 🔥 CREATE BILL
    @PostMapping("/create-bill")
    public BillEntity createBill(@RequestBody BillEntity bill) throws Exception {
        return paymentService.createOrderAndSave(bill);
    }

    // 🔥 VERIFY PAYMENT
    @PostMapping("/verify")
    public String verifyPayment(@RequestBody Map<String, String> data) {

        return paymentService.verifyAndUpdatePayment(
                data.get("razorpay_order_id"),
                data.get("razorpay_payment_id"),
                data.get("razorpay_signature")
        );
    }
}