package com.calpullix.service.product.list.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.calpullix.service.product.list.model.Product;
import com.calpullix.service.product.list.model.ProductHistory;


@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Integer> {
	
	@Query(value = "SELECT p FROM ProductHistory p WHERE YEAR(p.creationdate) = ?1")
	List<ProductHistory> findAllProductByYear(int year);
	
	ProductHistory findByIdproductAndStatusvalue(Product idproduct, Integer statusvalue);
	
}
