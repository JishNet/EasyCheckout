package com.example.EasyCheckout.Controller;

import com.example.EasyCheckout.Entity.ProductEntity;
import com.example.EasyCheckout.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addproduct")
    public ResponseEntity<String> addproduct(@RequestBody ProductEntity productEntity) {
        System.out.println(productEntity.getProductName());
          return   ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(productService.addproduct(productEntity)) ;
    }
    // 🔹 Generate QR for product
    @GetMapping("/{id}/qr")
    public ResponseEntity<byte[]> getQR(@PathVariable String id) throws Exception {

        byte[] qr = productService.generateQR(id);

        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(qr);
    }

    // 🔹 Scan API (frontend sends productId after scan)
    @GetMapping("/scan/{id}")
    public ResponseEntity<ProductEntity> scanProduct(@PathVariable String id) {

        ProductEntity product = productService.getProductById(id);

        return ResponseEntity.ok(product);
    }
}