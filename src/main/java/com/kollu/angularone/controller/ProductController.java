package com.kollu.angularone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kollu.angularone.model.ProductModel;
import com.kollu.angularone.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/test")
	public String welcome() {
		return "Testing endpoint is not secure";
	}

	@PostMapping("/saveproduct")
	public ProductModel create(@RequestBody ProductModel productModel) {
		return productService.save(productModel);
	}

	@GetMapping("/getproduct")
	public List<ProductModel> getAll() {
		return productService.getAll();
	}

	@PutMapping("/{id}")
	public ProductModel update(@PathVariable Long id, @RequestBody ProductModel productModel) {
		productModel.setId(id);
		return productService.save(productModel);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		productService.delete(id);
	}
}
