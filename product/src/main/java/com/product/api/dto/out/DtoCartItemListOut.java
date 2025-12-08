package com.product.api.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoCartItemListOut {

    @JsonProperty("cart_item_id")
    private Integer cartItemId;

    @JsonProperty("product")
    private String product;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("subtotal")
    private Double subtotal;

    public DtoCartItemListOut(Integer cartItemId, String product, Integer quantity, Double price, Double subtotal) {
        this.cartItemId = cartItemId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }
}
