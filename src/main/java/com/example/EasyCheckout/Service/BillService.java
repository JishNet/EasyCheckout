package com.example.EasyCheckout.Service;
import com.example.EasyCheckout.Entity.BillEntity;
import com.example.EasyCheckout.Entity.BillItems;
import com.example.EasyCheckout.dto.BillRequest;
import com.example.EasyCheckout.Entity.ProductEntity;
import com.example.EasyCheckout.Exception.InsufficientStockException;
import com.example.EasyCheckout.Exception.ProductNotFoundException;
import com.example.EasyCheckout.Repositry.BillRepo;
import com.example.EasyCheckout.Repositry.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class BillService {

    @Autowired
    private BillRepo billRepo;

    @Autowired
    private QRService qrService ;

    @Autowired
    private ProductRepo productRepo;

    public BillEntity createBill(BillRequest billRequest) {

        if (billRequest.getBillitem() == null || billRequest.getBillitem().isEmpty()) {
            throw new RuntimeException("Bill must contain items");
        }

        double total = 0;

        for (BillItems item : billRequest.getBillitem()) {

            ProductEntity product = productRepo.findById(item.getId())
                    .orElseThrow(() -> new ProductNotFoundException(
                            "Product not found: " + item.getId()
                    ));

            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("Stock is Insufficient for "+product.getProductName());
            }

            total += product.getPrice() * item.getQuantity();
        }

        BillEntity bill = new BillEntity();

        bill.setStoreID(billRequest.getStoreID());
        bill.setCustomerphone(billRequest.getUserphone());
        bill.setItems(billRequest.getBillitem());
        bill.setStatus("CREATED");
        bill.setTotalprice(total);
        bill.setCreatedAt(LocalDateTime.now());

        return billRepo.save(bill);
    }

    public BillEntity markBillAsPaid(String billId) throws Exception {

        BillEntity bill = billRepo.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        bill.setStatus("PAID");

        String qrToken = qrService.generateQRToken(billId);
        bill.setQrToken(qrToken);

        return billRepo.save(bill);
    }

}
