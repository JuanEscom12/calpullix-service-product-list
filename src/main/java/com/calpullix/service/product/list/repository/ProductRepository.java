package com.calpullix.service.product.list.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calpullix.service.product.list.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query("SELECT p FROM Product p WHERE name LIKE CONCAT('%',:name,'%') ")
	Optional<List<Product>> findByNameQueryProduct(@Param("name") String name);
	
}
