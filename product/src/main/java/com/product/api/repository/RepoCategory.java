package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.product.api.entity.Category;

public interface RepoCategory extends JpaRepository<Category,Integer> {

	@Query(value="SELECT * FROM category ORDER BY category", nativeQuery=true)
	List<Category> getCategories();
	
	
}
