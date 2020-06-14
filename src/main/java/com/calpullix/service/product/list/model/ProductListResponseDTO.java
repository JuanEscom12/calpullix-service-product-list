package com.calpullix.service.product.list.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponseDTO {
	
	private List<ProductDetailDTO> productDetail;
	
}
