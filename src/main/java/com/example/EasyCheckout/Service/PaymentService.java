package com.example.EasyCheckout.Service;

import com.example.EasyCheckout.Entity.BillEntity;
import com.example.EasyCheckout.Repositry.BillRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    @Autowired
    private BillRepo billRepo;

    // 🔥 CREATE ORDER
    public Map<String, Object> createOrderAndSave(BillEntity bill) throws Exception {

        // 🔥 ADD THIS FIRST
        System.out.println("KEY ID => " + keyId);
        System.out.println("KEY SECRET => " + keySecret);
        System.out.println("TOTAL PRICE => " + bill.getTotalprice());

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", (int)Math.round(bill.getTotalprice() * 100));
        options.put("currency", "INR");
        options.put("receipt", "bill_" + System.currentTimeMillis());

        System.out.println("Creating Razorpay order...");

        Order razorOrder = client.orders.create(options);

        System.out.println("ORDER CREATED => " + razorOrder);

        bill.setRazorpayOrderId(razorOrder.get("id"));
        bill.setPaymentStatus("CREATED");

        billRepo.save(bill);

        Map<String, Object> response = new HashMap<>();
        response.put("key", keyId);
        response.put("orderId", razorOrder.get("id"));
        response.put("amount", razorOrder.get("amount"));

        return response;
    }    // 🔥 VERIFY + UPDATE
    public String verifyAndUpdatePayment(String orderId, String paymentId, String signature) {

        try {
            String payload = orderId + "|" + paymentId;
            String generatedSignature = Utils.getHash(payload, keySecret);

            BillEntity bill = billRepo.findByRazorpayOrderId(orderId);

            if (bill == null) {
                throw new RuntimeException("Bill not found");
            }

            if (generatedSignature.equals(signature)) {
                bill.setPaymentId(paymentId);
                bill.setPaymentStatus("PAID");

                billRepo.save(bill);
                return "Payment Successful";
            } else {
                bill.setPaymentStatus("FAILED");
                billRepo.save(bill);
                throw new RuntimeException("Payment verification failed");
            }

        } catch (Exception e) {
            throw new RuntimeException("Something went wrong");
        }
    }
}