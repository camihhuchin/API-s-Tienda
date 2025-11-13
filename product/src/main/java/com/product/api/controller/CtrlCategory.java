package com.product.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.*;

@RestController
@RequestMapping("/category")
@Tag(name = "Category", description = "Módulo para la gestión de categorías de productos")
public class CtrlCategory {
	
	@Autowired
	SvcCategory svc;

	@GetMapping
    @Operation(summary = "Obtener todas las categorías", description = "Devuelve la lista completa de categorías registradas")
	public ResponseEntity<List<Category>> getCategories(){
		return ResponseEntity.ok(svc.findAll());
	}
	
	@GetMapping("/active")
    @Operation(summary = "Obtener categorías activas", description = "Devuelve las categorías con estado activo")
	public ResponseEntity<List<Category>> findActive(){
		return ResponseEntity.ok(svc.findActive());
	}
	
	@PatchMapping("/{id}/disable")
    @Operation(summary = "Desactivar categoría", description = "Cambia el estado de una categoría a inactiva")
	public ResponseEntity<ApiResponse> disable(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.disable(id));
	}
	
	@PatchMapping("/{id}/enable")
    @Operation(summary = "Activar categoría", description = "Cambia el estado de una categoría a activa")
	public ResponseEntity<ApiResponse> enable(@PathVariable Integer id) {
		return ResponseEntity.ok(svc.enable(id));
	}

	// Lanza ApiException si el nombre o el tag ya están registrados
	@PostMapping
    @Operation(summary = "Registrar nueva categoría", description = "Registra una nueva categoría verificando que el nombre y el tag no estén duplicados")
	public ResponseEntity<ApiResponse> create(@Valid@RequestBody DtoCategoryIn in){
		return ResponseEntity.ok(svc.create(in));
	}
	
	// Lanza ApiException si el id
	@PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría", description = "Actualiza los datos de una categoría existente por su ID")
	public ResponseEntity<ApiResponse> update(@PathVariable("id") Integer id,@Valid @RequestBody DtoCategoryIn in){
		return ResponseEntity.ok(svc.update(in, id));
	}

}
