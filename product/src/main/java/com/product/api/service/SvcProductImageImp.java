package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProductImage;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcProductImageImp implements SvcProductImage {
	
	 @Autowired
    RepoProductImage repo;

    @Value("${app.upload.dir}")
    private String uploadDir;
    
    @Override
    public ResponseEntity<ProductImage[]> getProductImages(Integer productId) {
        try {
            ProductImage[] productImgs = repo.findByProductId(productId);
            return new ResponseEntity<>(productImgs, HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
    
    @Override
    public ResponseEntity<ApiResponse> uploadProductImage(DtoProductImageIn in) {
        try {
            // limpiar el prefijo data:image si existe
            if (in.getImage().startsWith("data:image")) {
                int commaIndex = in.getImage().indexOf(",");
                if (commaIndex != -1) {
                    in.setImage(in.getImage().substring(commaIndex + 1));
                }
            }

            byte[] imageBytes = Base64.getDecoder().decode(in.getImage());
            String fileName = UUID.randomUUID().toString() + ".png";

            Path imagePath = Paths.get(uploadDir, "img", "product", fileName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, imageBytes);

            ProductImage productImage = new ProductImage();
            productImage.setProductId(in.getProductId());
            productImage.setImage("/img/product/" + fileName);
            productImage.setStatus(1);

            repo.save(productImage);

            return new ResponseEntity<>(new ApiResponse("La imagen ha sido registrada"), HttpStatus.OK);

        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

	
    @Override
    public ResponseEntity<ApiResponse> deleteProductImage(Integer productImageId) {
        try {
            repo.disableProductImage(productImageId);
            return new ResponseEntity<>(new ApiResponse("La imagen ha sido eliminada"), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
	
}


