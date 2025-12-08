package com.product.api.commons.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.api.dto.out.DtoCartItemListOut;
import com.product.api.entity.CartItem;
import com.product.api.entity.Product;
import com.product.api.repository.RepoProduct;


@Service
public class MapperCartItem {

    @Autowired
    private RepoProduct repoProduct;

    public DtoCartItemListOut fromCartItem(CartItem cartItem) {

        Product product = repoProduct.findByGtin(cartItem.getGtin());

        String name = (product != null) ? product.getProduct() : "Producto no encontrado";
        Double price = (product != null) ? product.getPrice() : 0.0;

        Double subtotal = price * cartItem.getQuantity();

        return new DtoCartItemListOut(
                cartItem.getCartItemId(),
                name,
                cartItem.getQuantity(),
                price,
                subtotal
        );
    }

    public List<DtoCartItemListOut> fromCartItemList(List<CartItem> items) {
        List<DtoCartItemListOut> list = new ArrayList<>();
        for (CartItem item : items) {
            list.add(fromCartItem(item));
        }
        return list;
    }
}
