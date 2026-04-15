package com.example.EasyCheckout.Controller;
import com.example.EasyCheckout.Entity.BillRequest;
import com.example.EasyCheckout.Service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bill")
public class BillController {

    @Autowired
    BillService billService ;

    @PostMapping("/createbill")
    public ResponseEntity<?> createBill(@RequestBody BillRequest billRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(billService.createBill(billRequest));
    }
}
