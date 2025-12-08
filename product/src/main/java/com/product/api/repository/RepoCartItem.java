package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.dto.out.DtoCartItemListOut;
import com.product.api.entity.CartItem;

import jakarta.transaction.Transactional;

@Repository
public interface RepoCartItem extends JpaRepository<CartItem, Integer> {
    
    @Query(value =
            "SELECT ci.cart_item_id,ci.user_id, ci.gtin, p.product, ci.status,ci.quantity, p.price, " +
            "       (ci.quantity * p.price) AS subtotal " +
            "FROM cart_item ci " +
            "INNER JOIN product p ON p.gtin = ci.gtin " +
            "WHERE ci.user_id = :userId " +
            "ORDER BY ci.cart_item_id",
            nativeQuery = true)
    List<CartItem> getCartItems(@Param("userId") Integer userId);

    
    @Query(value = "SELECT * FROM cart_item " +
            "WHERE user_id = :userId AND gtin = :gtin AND status = :status " +
            "LIMIT 1", nativeQuery = true)
	CartItem findByUserIdAndGtinAndStatus(
	     @Param("userId") Integer userId,
	     @Param("gtin") String gtin,
	     @Param("status") Integer status);
    
    @Query(value = "SELECT * FROM cart_item " +
            "WHERE user_id = :userId AND cart_item_id = :cart_item_id "
            , nativeQuery = true)
	CartItem findByUserIdAndId(
	     @Param("userId") Integer userId,
	     @Param("cart_item_id") Integer cart_item_id);



    @Query(value =
        "SELECT ci.cart_item_id, p.product, ci.quantity, p.price, " +
        "       (ci.quantity * p.price) AS subtotal " +
        "FROM cart_item ci " +
        "INNER JOIN product p ON p.gtin = ci.gtin " +
        "WHERE ci.cart_item_id = :id AND ci.user_id = :userId",
        nativeQuery = true)
    DtoCartItemListOut getCartItem(
        @Param("id") Integer id,
        @Param("userId") Integer userId
    );


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value =
        "INSERT INTO cart_item(user_id, gtin, quantity, status) " +
        "VALUES (:userId, :gtin, :quantity, 1)",
        nativeQuery = true)
    void create(
        @Param("userId") Integer userId,
        @Param("gtin") String gtin,
        @Param("quantity") Integer quantity
    );


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value =
        "UPDATE cart_item " +
        "SET quantity = :quantity " +
        "WHERE cart_item_id = :id AND user_id = :userId",
        nativeQuery = true)
    void updateQuantity(
        @Param("id") Integer id,
        @Param("userId") Integer userId,
        @Param("quantity") Integer quantity
    );


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value =
        "DELETE FROM cart_item " +
        "WHERE cart_item_id = :id AND user_id = :userId",
        nativeQuery = true)
    void deleteItem(
        @Param("id") Integer id,
        @Param("userId") Integer userId
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value =
        "DELETE FROM cart_item WHERE user_id = :userId",
        nativeQuery = true)
    void emptyCart(@Param("userId") Integer userId);
}

