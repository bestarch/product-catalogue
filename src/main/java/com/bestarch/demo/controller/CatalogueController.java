package com.bestarch.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bestarch.demo.model.Product;
import com.bestarch.demo.service.ProductService;
import com.bestarch.demo.util.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/product")
public class CatalogueController {
	
	@Autowired
	private Utility utility;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<String> getProduct(@PathVariable("id") Integer productId) throws JsonProcessingException {
		return new ResponseEntity<>(productService.getProduct(productId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Integer> createNewProduct(@RequestBody Product product) throws JsonProcessingException {
		return new ResponseEntity<>(productService.createNewProduct(product), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/random")
	public ResponseEntity<List<Integer>> createRandomProducts(@RequestParam(defaultValue = "100") Integer n) throws JsonProcessingException {
		List<Integer> ids = productService.createRandomProducts(n);
		return new ResponseEntity<>(ids, HttpStatus.CREATED);
	}

}
