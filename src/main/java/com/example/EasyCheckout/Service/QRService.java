package com.example.EasyCheckout.Service;

import com.example.EasyCheckout.Entity.BillEntity;
import com.example.EasyCheckout.Repositry.BillRepo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class QRService {
    @Autowired
    BillRepo billRepo ;

    private final String SECRET = "mysecret123"; // move to env later

    // 🔐 Generate secure token
    public String generateQRToken(String billId) {
        String raw = billId + "|" + SECRET;
        return Base64.getEncoder().encodeToString(raw.getBytes());
    }

    // 🧾 Generate QR Image
    public byte[] generateQRCodeImage(String data) throws Exception {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }
    
    public boolean verifyQR(String qrData) {

        String[] parts = qrData.split("\\|");

        String billId = parts[0];
        String token = parts[1];

        BillEntity bill = billRepo.findById(billId)
                .orElseThrow(() -> new RuntimeException("Invalid Bill"));

        // 🔐 match token
        if (!bill.getQrToken().equals(token)) {
            return false;
        }

        return "PAID".equals(bill.getStatus());
    }

}