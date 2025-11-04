package com.product.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

import jakarta.validation.Valid;

import java.util.*;

@RestController
@RequestMapping("/category")
public class CtrlCategory {
	
	@Autowired
	SvcCategory svc;

	@GetMapping
	public ResponseEntity<List<Category>> getCategories(){
		return ResponseEntity.ok(svc.findAll());
	}
	
	
	
	@GetMapping("/active")
	public ResponseEntity<List<Category>> findActive(){
		return ResponseEntity.ok(svc.findActive());
	}

	// Lanza ApiException si el nombre o el tag ya están registrados
	@PostMapping
	public ResponseEntity<ApiResponse> create(@Valid@RequestBody DtoCategoryIn in){
		return ResponseEntity.ok(svc.create(in));
	}
	
	// Lanza ApiException si el id
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> update(@PathVariable("id") Integer id,@Valid @RequestBody DtoCategoryIn in){
		return ResponseEntity.ok(svc.update(in, id));
	}

	@PatchMapping("/{id}/enable")
	public ResponseEntity<ApiResponse> enable(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.enable(id));
	}


	@PatchMapping("/{id}/disable")
	public ResponseEntity<ApiResponse> disable(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.disable(id));
	}

}
