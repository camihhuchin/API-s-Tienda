package com.product.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoCartItemIn;
import com.product.api.dto.out.DtoCartItemListOut;
import com.product.api.entity.CartItem;

public interface SvcCartItem {

	public ResponseEntity<List<DtoCartItemListOut>> getCartItems(Integer userId);

    public ResponseEntity<DtoCartItemListOut> getCartItem(Integer id, Integer userId);

    public ResponseEntity<ApiResponse> createCartItem(Integer userId, DtoCartItemIn in);

    public ResponseEntity<ApiResponse> deleteCartItem(Integer userId, Integer cartItemId);

    public ResponseEntity<ApiResponse> emptyCart(Integer userId);

}
