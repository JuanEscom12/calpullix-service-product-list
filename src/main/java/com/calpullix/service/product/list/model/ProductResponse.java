package com.calpullix.service.product.list.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

	private Integer id;
	
	private String content;
	
	private String name;
	
	private String description;
	
	private String brand;
	
	private BigDecimal size;
	
	private String color;
	
	private String material;
	
	private BigDecimal price;
	
}
