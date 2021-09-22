package com.bestarch.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bestarch.demo.model.Product;
import com.bestarch.demo.util.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductService {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private Utility utility;
	
	private ObjectMapper mapper = new ObjectMapper();

	public String getProduct(Integer productId) throws JsonProcessingException {
		Map<Object, Object> map = redisTemplate.opsForHash().entries("product:"+productId);
		return mapper.writeValueAsString(map);
	}
	
	public String getCategories() throws JsonProcessingException {
		Set<Object> categories = redisTemplate.opsForSet().members("categories");
		return mapper.writeValueAsString(categories);
	}

	public void setCategories(List<String> categories) throws JsonProcessingException {
		redisTemplate.opsForSet().add("categories", categories.toArray());
	}

	public Integer createNewProduct(Product product) {
		Map<Object, Object> map = new HashMap<>();
		int id = new Random().nextInt(99);
		map.put("id", id+1);
		map.put("category", StringUtils.capitalize(product.getCategory()));
		map.put("desc", product.getDesc());
		map.put("discount", product.getDiscount());
		map.put("name", product.getName());
		map.put("price", product.getPrice());
		redisTemplate.opsForHash().putAll("product:"+(id+1), map);
		return id+1;
	}

	public List<Integer> createRandomProducts(Integer n) {
		Map<Object, Object> map = new HashMap<>();
		List<Integer> ids = new ArrayList<>();
		int start = new Random().nextInt(99);
		for (int i = start+1;i<=n; i++) {
			map.put("id", i);
			map.put("category", StringUtils.capitalize(utility.randomCategory().name()));
			map.put("desc", "product with description ");
			map.put("discount", new Random().nextDouble());
			map.put("name", "Product "+i);
			map.put("price", (new Random().nextInt(500)+10)*1.0);
			redisTemplate.opsForHash().putAll("product:"+i, map);
			ids.add(i);
			map.clear();
		}
		return ids;
	}
}
