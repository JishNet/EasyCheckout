package com.example.EasyCheckout.Controller;

import com.example.EasyCheckout.Entity.BillEntity;
import com.example.EasyCheckout.Repositry.BillRepo;
import com.example.EasyCheckout.Service.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/qr")
public class QRController {

    @Autowired
    private BillRepo billRepository;

    @Autowired
    private QRService qrService;

    @GetMapping("/{billId}")
    public ResponseEntity<byte[]> getReceiptQR(@PathVariable String billId) throws Exception {

        BillEntity bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if (!"PAID".equals(bill.getStatus())) {
            throw new RuntimeException("Bill not paid");
        }

        // 🔥 QR contains billId + token
        String qrData = bill.getBillId() + "|" + bill.getQrToken();

        byte[] qrImage = qrService.generateQRCodeImage(qrData);

        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(qrImage);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyQR(@RequestBody String qrData) {

        boolean isValid = qrService.verifyQR(qrData);

        if (!isValid) {
            return ResponseEntity.badRequest().body("Invalid QR");
        }

        return ResponseEntity.ok("Valid Receipt ✅");
    }

}