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
public class ProductDetailDTO {

	private Integer id;
	
	private String description;
	
	private String name;
	
	private String brand;
	
	private String status;
	
	private String branch;
	
	private BigDecimal monthlyAverageSales;
	
	private BigDecimal dailyAverageSales;
	
	private String weight;
	
	private BigDecimal salePrice;
	
	private Integer totalRows;
	
	
}
