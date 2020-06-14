package com.calpullix.service.product.list.service;

import java.io.IOException;
import java.util.List;

import com.calpullix.service.product.list.model.DataSheetDetailDTO;
import com.calpullix.service.product.list.model.ProductDTO;
import com.calpullix.service.product.list.model.ProductDetailRequestDTO;
import com.calpullix.service.product.list.model.ProductList;
import com.calpullix.service.product.list.model.ProductListRequest;
import com.calpullix.service.product.list.model.ProductListResponseDTO;

public interface ProductService {

	boolean saveImage() throws IOException;
	
	ProductDTO getProduct(Integer id);
	
	ProductListResponseDTO getProductByFilters(ProductListRequest request);
	
	ProductDTO getProductDetail(ProductDetailRequestDTO request);
	
	List<DataSheetDetailDTO> getDataSheetDetail(ProductDetailRequestDTO request);
	
	ProductList getProductList(ProductDetailRequestDTO request);
	
}
