package com.example.EasyCheckout.Service;

import com.example.EasyCheckout.Entity.BillEntity;
import com.example.EasyCheckout.Repositry.BillRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
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

    // ==============================
    // CREATE ORDER
    // ==============================
    public Map<String, Object> createOrderAndSave(BillEntity bill) throws Exception {

        System.out.println("TOTAL PRICE => " + bill.getTotalprice());

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", (int) Math.round(bill.getTotalprice() * 100));
        options.put("currency", "INR");
        options.put("receipt", "bill_" + System.currentTimeMillis());

        Order razorOrder = client.orders.create(options);

        bill.setRazorpayOrderId(razorOrder.get("id").toString());
        bill.setPaymentStatus("CREATED");

        billRepo.save(bill);

        Map<String, Object> response = new HashMap<>();
        response.put("key", keyId);
        response.put("orderId", razorOrder.get("id"));
        response.put("amount", razorOrder.get("amount"));

        return response;
    }

    // ==============================
    // VERIFY PAYMENT
    // ==============================
    public String verifyAndUpdatePayment(String orderId, String paymentId, String signature) {

        try {

            BillEntity bill = billRepo.findByRazorpayOrderId(orderId);

            if (bill == null) {
                throw new RuntimeException("Bill not found for orderId: " + orderId);
            }

            String payload = orderId + "|" + paymentId;

            String generatedSignature = hmacSha256(payload, keySecret);

            System.out.println("Generated Signature => " + generatedSignature);
            System.out.println("Received Signature   => " + signature);

            if (generatedSignature.equals(signature)) {

                bill.setPaymentId(paymentId);
                bill.setPaymentStatus("PAID");

                billRepo.save(bill);

                return "Payment Successful";

            } else {

                bill.setPaymentStatus("FAILED");
                billRepo.save(bill);

                return "Payment verification failed";
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Verification error: " + e.getMessage());
        }
    }

    // ==============================
    // HMAC SHA256 SIGNATURE
    // ==============================
    private String hmacSha256(String data, String secret) throws Exception {

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return Base64.getEncoder().encodeToString(
                sha256_HMAC.doFinal(data.getBytes())
        );
    }
}