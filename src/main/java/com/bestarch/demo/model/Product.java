package com.bestarch.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

	private Integer id;
	private String name;
	private String desc;
	private Double price;
	private String category;
	private Double discount;

}
