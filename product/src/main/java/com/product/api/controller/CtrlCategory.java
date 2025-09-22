package com.product.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

import java.util.*;

@RestController
@RequestMapping("/category")
public class CtrlCategory {
	
	@Autowired
	SvcCategory svc;

	@GetMapping
	public ResponseEntity<List<Category>> getCategories(){
		return svc.getCategories();

	}


}
