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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.digest.HmacUtils.hmacSha256;

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

        System.out.println("TOTAL PRICE => " + bill.getTotalprice());

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", (int)Math.round(bill.getTotalprice() * 100));
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
    public String verifyAndUpdatePayment(String orderId, String paymentId, String signature) {

        try {
            BillEntity bill = billRepo.findByRazorpayOrderId(orderId);

            if (bill == null) {
                throw new RuntimeException("Bill not found for orderId: " + orderId);
            }

            String payload = orderId + "|" + paymentId;

            String generatedSignature = Arrays.toString(hmacSha256(payload, keySecret));

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
            e.printStackTrace(); // IMPORTANT for Render logs
            throw new RuntimeException("Verification error: " + e.getMessage());
        }
    }}