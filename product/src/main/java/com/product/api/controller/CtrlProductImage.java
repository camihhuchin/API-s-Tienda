package com.product.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.service.SvcProductImage;
import com.product.exception.ApiException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product/{product_id}/image")
public class CtrlProductImage {
		
    @Autowired
    SvcProductImage svc;

    @GetMapping
    public ResponseEntity<ProductImage[]> getProductImages(@PathVariable("product_id") Integer productId) {
        return svc.getProductImages(productId);
    }
	
   @PostMapping
   public ResponseEntity<ApiResponse> createProductImage(
           @PathVariable("product_id") Integer productId,
           @Valid @RequestBody DtoProductImageIn in,
           BindingResult bindingResult) {

       if (bindingResult.hasErrors())
           throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

       in.setProductId(productId);
       return svc.uploadProductImage(in);
   }
    
   @DeleteMapping("/{product_image_id}")
   public ResponseEntity<ApiResponse> deleteProductImage(
           @PathVariable("product_image_id") Integer productImageId) {
       return svc.deleteProductImage(productImageId);
   }
    
    
}


