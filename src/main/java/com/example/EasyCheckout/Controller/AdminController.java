package com.example.EasyCheckout.Controller;
import com.example.EasyCheckout.Entity.AdminEntity;
import com.example.EasyCheckout.dto.AdminRequest;
import com.example.EasyCheckout.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService ;

    @PostMapping("/signup")
    public ResponseEntity<?> adminsignup (@RequestBody AdminEntity adminEntity) {
           return
           ResponseEntity
                   .status(HttpStatus.CREATED)
                   .body(adminService.adminsignup(adminEntity)) ;

    }

    @PostMapping("/login")
    public ResponseEntity<?> adminlogin(@RequestBody AdminRequest adminRequest) {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(adminService.adminLogin(adminRequest));
    }


}
