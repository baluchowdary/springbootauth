package com.kollu.angularone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollu.angularone.model.ProductModel;
import com.kollu.angularone.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public List<ProductModel> getAll() {
		return repository.findAll();
	}

	public ProductModel getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public ProductModel save(ProductModel productModel) {
		return repository.save(productModel);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
