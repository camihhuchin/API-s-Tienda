package com.product.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductOut;
import com.product.api.entity.Product;

@Repository
public interface RepoProduct extends JpaRepository<Product, Integer> {

	@Query(value="SELECT p.*, c.category"
			+" FROM product p "
			+"INNER JOIN category c ON c.category_id=p.category_id "
			+" WHERE p.product_id = :product_id", nativeQuery=true)
	DtoProductOut getProduct(Integer product_id);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
            FROM product WHERE gtin = :gtin
            """, nativeQuery = true)
    boolean existsByGtin(@Param("gtin") String gtin);

    @Query(value="SELECT p.*, c.category"
			+" FROM product p "
			+"INNER JOIN category c ON c.category_id=p.category_id "
			+" WHERE p.gtin = :gtin", nativeQuery=true)
    DtoProductOut getProductByGtin(@Param("gtin") String gtin);
    
    Product findByGtin(String gtin);
    
    
}

