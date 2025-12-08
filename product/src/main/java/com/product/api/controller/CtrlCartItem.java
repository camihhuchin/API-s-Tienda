package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoCartItemIn;
import com.product.api.dto.out.DtoCartItemListOut;
import com.product.api.entity.CartItem;
import com.product.api.service.SvcCartItem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart-item")
@Tag(name = "CartItem", description = "Módulo para administrar items del carrito")
public class CtrlCartItem {

    @Autowired
    private SvcCartItem svc;

    private Integer extractUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }

    @GetMapping
    @Operation(summary = "Obtener carrito", description = "Obtiene todos los items activos del carrito del usuario")
    public ResponseEntity<List<DtoCartItemListOut>> getCartItems(HttpServletRequest request) {
        Integer userId = extractUserId(request);
        return svc.getCartItems(userId);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener item específico", description = "Obtiene la información de un item del carrito")
    public ResponseEntity<DtoCartItemListOut> getCartItem(
            @PathVariable Integer id,
            HttpServletRequest request) {

        Integer userId = extractUserId(request);
        return svc.getCartItem(id, userId);
    }

    @PostMapping
    @Operation(summary = "Agregar item al carrito", description = "Agrega un producto al carrito o aumenta su cantidad")
    public ResponseEntity<ApiResponse> createCartItem(
            HttpServletRequest request,
            @Valid @RequestBody DtoCartItemIn in) {

        Integer userId = extractUserId(request);
        return svc.createCartItem(userId, in);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar item del carrito", description = "Realiza un borrado lógico del item")
    public ResponseEntity<ApiResponse> deleteCartItem(
            @PathVariable Integer id,
            HttpServletRequest request) {

        Integer userId = extractUserId(request);
        return svc.deleteCartItem(id, userId);
    }

    @DeleteMapping
    @Operation(summary = "Vaciar carrito", description = "Marca como eliminados todos los items del carrito")
    public ResponseEntity<ApiResponse> emptyCart(HttpServletRequest request) {
        Integer userId = extractUserId(request);
        return svc.emptyCart(userId);
    }
}
