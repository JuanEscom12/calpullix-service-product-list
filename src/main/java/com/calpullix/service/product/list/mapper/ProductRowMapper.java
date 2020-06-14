package com.calpullix.service.product.list.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.calpullix.service.product.list.model.ProductDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductRowMapper implements RowMapper<ProductDTO> {

	@Override
	public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductDTO product = new ProductDTO();
		product.setId(rs.getInt("ID"));
		product.setName(rs.getString("NAME"));
		
		try {
			
			product.setPictureResult(rs.getBlob("PHOTO").getBytes(1, new Long(rs.getBlob("PHOTO").length()).intValue()));
			rs.getBlob("PHOTO").free();
			log.info(":::::::::::: IMAGE {} ", new String(product.getPictureResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		product.setIsIndividualPackaging(Boolean.TRUE);
		product.setIsCofeprisPermission(Boolean.TRUE);
		product.setIsFragileMaterial(Boolean.TRUE);
		product.setQuantity(2907);
		product.setProvider("Lala");
		product.setPurchasePrice(new BigDecimal("127989.90"));
		product.setNetIncome(BigDecimal.ONE);
		product.setTaxes(new BigDecimal("289.90"));
		product.setCategory("Electronica");
		product.setMeasurements("25 x 10 x 9 cms");
		product.setProductClassification("Clase A");
			
		
		
		return product;
	}

}
