package com.calpullix.service.product.list.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductListRequest {
	
	private Integer page;
	
	private Integer branchId;
	
	private String product;
	
}
