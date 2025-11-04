package com.product.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.entity.ProductImage;

import jakarta.transaction.Transactional;

@Repository
public interface RepoProductImage extends JpaRepository<ProductImage,Integer>{
	
	 @Query(value = "SELECT * FROM product_image WHERE product_id = :product_id AND status = 1", nativeQuery = true)
	    ProductImage[] findByProductId(@Param("product_id") Integer productId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE product_image SET status = 0 WHERE product_image_id = :product_image_id", nativeQuery = true)
    void disableProductImage(@Param("product_image_id") Integer productImageId);
}
