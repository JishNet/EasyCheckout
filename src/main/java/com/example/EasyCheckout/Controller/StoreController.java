package com.example.EasyCheckout.Controller;

import com.example.EasyCheckout.Entity.StoreEntity;
import com.example.EasyCheckout.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/store")
public class StoreController {

    @Autowired
    StoreService storeService ;

    @PostMapping("/addstore")
    public ResponseEntity<?> addstore(@RequestBody StoreEntity storeEntity) {

        try {
            return  ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(storeService.saveStore(storeEntity)) ;
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Store Details can not be created") ;
        }

    }

    @GetMapping("/addstore")
    public ResponseEntity<?> fetchstore() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(storeService.getStore());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to fetch stores");
        }
    }

    @DeleteMapping("/deletestore/{storeID}")
    public ResponseEntity<?> deletestore(@PathVariable String  storeID) {

        try{
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(storeService.deleteStore(storeID)) ;
        } catch (Exception e) {
            return  ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Store data can not be deleted") ;
        }

    }


}
