package com.calpullix.service.product.list.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {

	private Integer id;
	
	private Boolean isIndividualPackaging;
	
	private Boolean isCofeprisPermission;
	
	private Boolean isFragileMaterial;
	
	private Integer quantity;
	
	private String provider;
	
	private BigDecimal purchasePrice;
	
	private String name;
	
	private String description;
	
	private String brand;
	
	private BigDecimal netIncome;
	
	private BigDecimal profitabilityPercentage;
	
	private BigDecimal taxes;
	
	private String category;
	
	private String measurements;
	
	private String productClassification;

	private byte[] pictureResult;
	
	private byte[] picture;
		
}
