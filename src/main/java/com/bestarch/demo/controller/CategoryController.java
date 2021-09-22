package com.bestarch.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestarch.demo.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private ProductService productService;
	

	@GetMapping
	public ResponseEntity<String> getCategories() throws JsonProcessingException {
		return new ResponseEntity<>(productService.getCategories(), HttpStatus.OK);
	}
	
	@PutMapping()
	public ResponseEntity<String> setCategories(@RequestBody List<String> categories) throws JsonProcessingException {
		productService.setCategories(categories);
		return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
	}
	
}
