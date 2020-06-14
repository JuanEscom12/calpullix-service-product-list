package com.calpullix.service.product.list.dao;

import com.calpullix.service.product.list.model.ProductDTO;

public interface ProductDAO {

	int saveProduct(ProductDTO product);

	ProductDTO getProduct(Integer id);
}
