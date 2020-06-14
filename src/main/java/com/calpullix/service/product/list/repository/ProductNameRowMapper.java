package com.calpullix.service.product.list.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.calpullix.service.product.list.model.Product;



public class ProductNameRowMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		final Product result = new Product();
		result.setId(rs.getInt("id"));
		result.setName(rs.getString("name"));
		return result;
	}

}
