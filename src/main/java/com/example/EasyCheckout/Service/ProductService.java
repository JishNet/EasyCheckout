package com.example.EasyCheckout.Service;

import com.example.EasyCheckout.Entity.ProductEntity;
import com.example.EasyCheckout.Exception.ProductNotFoundException;
import com.example.EasyCheckout.Repositry.ProductRepo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepository;


    public String addproduct(ProductEntity productEntity) {
        productRepository.save(productEntity) ;
        return "Product Added SuccesFully " ;
    }
    // ✅ Generate QR (only productId)
    public byte[] generateQR(String productId) throws Exception {

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(productId,
                BarcodeFormat.QR_CODE, 250, 250);

        BufferedImage image = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < 250; x++) {
            for (int y = 0; y < 250; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(image, "png", output);

        return output.toByteArray();
    }

    // ✅ Fetch product after scan
    public ProductEntity getProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}