package com.bestarch.demo.util;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.bestarch.demo.model.Category;

@Component
public class Utility {

	public Category randomCategory() {
		Category[] categories = Category.values();
		int size = categories.length;
		int toSelect = new Random().nextInt(size);
		return categories[toSelect];
	}
}
