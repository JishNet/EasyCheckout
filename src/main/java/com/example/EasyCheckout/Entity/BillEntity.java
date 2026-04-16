package com.example.EasyCheckout.Entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;


@Document(collection = "Bills")
@Data
public class BillEntity {
    @Id
    private String billId ;
    private String storeID ;
    private String qrToken;   // 🔥 important (secure token)

    private Long customerphone  ;
    private List<BillItems> items ;
    private double totalprice ;
    private String status  ;
    private LocalDateTime createdAt ;

    private String razorpayOrderId;
    private String paymentId;
    private String paymentStatus;


}
