package com.example.EasyCheckout.Service;

import com.example.EasyCheckout.Entity.StoreEntity;
import com.example.EasyCheckout.Repositry.StoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    StoreRepo storeRepo ;

    public String saveStore (StoreEntity storeEntity) {
         storeRepo.save(storeEntity);
         return "Store details saved succefully" ;
    }

    public List<StoreEntity> getStore() {

           return  storeRepo.findAll() ;
    }

    public String deleteStore(String storeId) {
        storeRepo.deleteById(storeId) ;
        return "Store details deleted succesFully" ;
    }
}
