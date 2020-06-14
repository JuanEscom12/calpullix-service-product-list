package com.calpullix.service.product.list.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calpullix.service.product.list.model.DataSheet;
import com.calpullix.service.product.list.model.Product;

@Repository
public interface DataSheetRepository extends JpaRepository<DataSheet, Integer> {

	List<DataSheet> findAllDataSheetByIdproduct(Product idproduct);
	
}
