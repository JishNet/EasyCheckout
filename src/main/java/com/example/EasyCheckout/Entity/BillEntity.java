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

    private Long Customerphone  ;
    private List<BillItems> Product ;
    private double totalprice ;
    private String status  ;
    private LocalDateTime createdAt ;

    private String razorpayOrderId;
    private String paymentId;
    private String paymentStatus;


}
