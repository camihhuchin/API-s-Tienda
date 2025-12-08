package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.commons.mapper.MapperCartItem;
import com.product.api.dto.in.DtoCartItemIn;
import com.product.api.dto.out.DtoCartItemListOut;
import com.product.api.dto.out.DtoProductOut;
import com.product.api.entity.CartItem;
import com.product.api.entity.Product;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;
import com.product.api.repository.RepoCartItem;
import com.product.api.repository.RepoProduct;

import jakarta.transaction.Transactional;

@Service
public class SvcCartItemImp implements SvcCartItem {

    @Autowired
    private RepoCartItem repo;

    @Autowired
    private RepoProduct repoProduct;
    
    @Autowired
    private MapperCartItem mapper;

    @Override
    public ResponseEntity<List<DtoCartItemListOut>> getCartItems(Integer userId) {

        List<CartItem> items = repo.getCartItems(userId);

        List<DtoCartItemListOut> dto = mapper.fromCartItemList(items);

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<DtoCartItemListOut> getCartItem(Integer id, Integer userId) {
        try {
            DtoCartItemListOut item = repo.getCartItem(id, userId);

            if (item == null)
                throw new ApiException(HttpStatus.NOT_FOUND, "El id del item no existe");

            return ResponseEntity.ok(item);

        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> createCartItem(Integer userId, DtoCartItemIn in) {
        try {

            DtoProductOut p = repoProduct.getProductByGtin(in.getGtin());
            if (p == null)
                throw new ApiException(HttpStatus.NOT_FOUND, "El GTIN no existe");

            if (p.getStock() < in.getQuantity())
                throw new ApiException(HttpStatus.BAD_REQUEST, "Stock insuficiente");

            CartItem existing = repo.findByUserIdAndGtinAndStatus(userId, in.getGtin(), 1);

            if (existing != null) {

                int newQuantity = existing.getQuantity() + in.getQuantity();

                if (p.getStock() < newQuantity)
                    throw new ApiException(HttpStatus.BAD_REQUEST, "Stock insuficiente");

                existing.setQuantity(newQuantity);
                repo.save(existing);

                return ResponseEntity.ok(new ApiResponse("Cantidad actualizada en el carrito"));
            }

            CartItem item = new CartItem();
            item.setUserId(userId);
            item.setGtin(in.getGtin());
            item.setQuantity(in.getQuantity());
            item.setStatus(1);

            repo.save(item);

            return new ResponseEntity<>(
                new ApiResponse("Producto agregado al carrito"),
                HttpStatus.CREATED
            );

        } catch (DataAccessException e) {
        	 e.printStackTrace();
            throw new DBAccessException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> deleteCartItem(Integer id, Integer userId) {
        try {

        	if (repo.findByUserIdAndId(userId, id) == null)
        	    throw new ApiException(HttpStatus.NOT_FOUND, "El id del item no existe");

            repo.deleteItem(id, userId);

            return ResponseEntity.ok(new ApiResponse("El item fue eliminado del carrito"));

        } catch (DataAccessException e) {
        	e.printStackTrace();
            throw new DBAccessException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> emptyCart(Integer userId) {
        try {
            repo.emptyCart(userId);
            return ResponseEntity.ok(new ApiResponse("El carrito ha sido vaciado"));
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
}
