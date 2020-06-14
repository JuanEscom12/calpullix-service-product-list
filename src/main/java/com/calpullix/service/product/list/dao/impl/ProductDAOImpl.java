package com.calpullix.service.product.list.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import com.calpullix.service.product.list.dao.ProductDAO;
import com.calpullix.service.product.list.mapper.ProductRowMapper;
import com.calpullix.service.product.list.model.ProductDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 

	@Override
	public int saveProduct(ProductDTO product) {
		final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("PRODUCT");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("NAME", product.getName());
		parameters.put("PHOTO", product.getPicture());
		return simpleJdbcInsert.execute(parameters);
	}

	@Override
	public ProductDTO getProduct(Integer id) {
		String query = "SELECT * FROM PRODUCT WHERE ID = ?";
		ProductDTO product = jdbcTemplate.queryForObject(
		    query, new Object[] { id }, new ProductRowMapper());
		log.info(":: Result DAO {} ", product);
		return product;
	}
	
}
