package com.example.EasyCheckout.Service;
import com.example.EasyCheckout.Entity.AdminEntity;
import com.example.EasyCheckout.Entity.AdminRequest;
import com.example.EasyCheckout.Exception.InvalidPasswordException;
import com.example.EasyCheckout.Exception.UserNotFoundException;
import com.example.EasyCheckout.Exception.UseralreadyexitsException;
import com.example.EasyCheckout.Repositry.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {


    @Autowired
    AdminRepo adminRepo;

    public AdminEntity adminsignup(AdminEntity adminEntity) {
        if (adminRepo.existsByEmail(adminEntity.getEmail())) {
            throw new UseralreadyexitsException("User already exits");
        }

        return adminRepo.save(adminEntity);
    }

    public AdminEntity adminLogin(AdminRequest adminRequest) {

        AdminEntity admin = adminRepo.findByEmail(adminRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (!adminRequest.getPassword().equals(admin.getPassword())) {
            throw new InvalidPasswordException("Password is invalid");
        }

        return admin;
    }
}

