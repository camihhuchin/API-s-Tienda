package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcCategoryImp implements SvcCategory{
	
	@Autowired
	RepoCategory repo;
	
	@Override
	public List<Category> findAll() {
		try {
			return repo.findAll();
		}catch (DataAccessException e) {
	        		throw new DBAccessException(e);
		}
	}

	@Override
	public List<Category> findActive() {
		try {
			return repo.findByStatusOrderByCategoryAsc(1);
		}catch (DataAccessException e) {
	        		throw new DBAccessException(e);
		}
	}

	@Override
	public ApiResponse create(DtoCategoryIn in) {
		try {
		repo.create(in.getCategory(), in.getTag());
		return new ApiResponse("La categoria ha sido registrada");
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_category"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoria ya está registrado");
			if (e.getLocalizedMessage().contains("ux_tag"))
				throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoria ya está registrado");
			throw new DBAccessException(e);
		}
	}

	@Override
	public ApiResponse update(DtoCategoryIn in, Integer id) {
		try {
			repo.update(id, in.getCategory(), in.getTag());
			return new ApiResponse("La región ha sido actualizada");
			}catch (DataAccessException e) {
				if(repo.findById(id).isEmpty())
					throw new ApiException(HttpStatus.NOT_FOUND, "El id de la región no existe");
				throw new DBAccessException(e);
			}
	}

	@Override
	public ApiResponse enable(Integer id) {
		try {
			validateId(id);
			repo.updateStatus(id, 1);
			return new ApiResponse("La región ha sido activada");

		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public ApiResponse disable(Integer id) {
		try {
			validateId(id);
			repo.updateStatus(id, 0);
			return new ApiResponse("La región ha sido activada");

		}catch (DataAccessException e) {
        		throw new DBAccessException(e);
		}
	}

	
	 private void validateId(Integer id) {
        if (repo.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe");
        }
	 }


}
