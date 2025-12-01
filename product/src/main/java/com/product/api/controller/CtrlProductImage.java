package com.product.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.service.SvcProductImage;
import com.product.exception.ApiException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/product/{product_id}/image")
@Tag(name = "Product Image", description = "Módulo para la gestión de imágenes de productos")
public class CtrlProductImage {

    @Autowired
    SvcProductImage svc;

    @DeleteMapping("/{product_image_id}")
    @Operation(summary = "Eliminar imagen", description = "Elimina (lógica o físicamente) una imagen asociada a un producto por su ID")
    public ResponseEntity<ApiResponse> deleteProductImage(
            @PathVariable("product_image_id") Integer productImageId) {
        return svc.deleteProductImage(productImageId);
    }

    @GetMapping
    @Operation(summary = "Obtener imágenes", description = "Obtiene todas las imágenes asociadas a un producto específico")
    public ResponseEntity<ProductImage[]> getProductImages(
            @PathVariable("product_id") Integer productId) {
        return svc.getProductImages(productId);
    }

    @PostMapping
    @Operation(summary = "Registrar imagen", description = "Guarda una nueva imagen asociada a un producto existente")
    public ResponseEntity<ApiResponse> createProductImage(
            @PathVariable("product_id") Integer productId,
            @Valid @RequestBody DtoProductImageIn in,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

        in.setProductId(productId);
        return svc.uploadProductImage(in);
    }
}
