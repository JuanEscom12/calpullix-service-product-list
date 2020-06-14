package com.calpullix.service.product.list.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.calpullix.service.product.list.model.Branch;
import com.calpullix.service.product.list.model.Product;
import com.calpullix.service.product.list.model.ProductBranch;

@Repository
public interface ProductBranchRepository extends JpaRepository<ProductBranch, Integer> {

	@Query("SELECT count(DISTINCT p.idproducthistory.idproduct) FROM ProductBranch p WHERE p.idbranch = ?1 "
			+ "AND p.idproducthistory.statusvalue = ?2")
	int countByBranch(Branch idbranch, Integer status);

	@Query("SELECT count(DISTINCT p.idproducthistory.idproduct) FROM ProductBranch p WHERE p.idbranch = ?1 "
			+ "AND p.idproducthistory.idproduct = ?2 AND p.idproducthistory.statusvalue = ?3")
	int countByBranchAndProduct(Branch idbranch, Product idproduct, Integer status);
	
	@Query("SELECT COUNT(*) FROM ProductBranch p WHERE p.idbranch = ?1 "
			+ "AND p.idproducthistory.idproduct = ?2 AND p.statusvalue = ?3 ")
	int getQuanityProductsByBranch(Branch idbranch, Product idproduct, Integer status);
	
	@Query("SELECT DISTINCT p.idproducthistory.idproduct FROM ProductBranch p WHERE p.idbranch = ?1 "
			+ "AND p.idproducthistory.statusvalue = ?2")
	List<Product> findAllProductBranchByIdbranch(Branch idbranch, Integer status, Pageable pagination);

	@Query("SELECT DISTINCT p.idproducthistory.idproduct FROM ProductBranch p WHERE p.idbranch = ?1 "
			+ "AND p.idproducthistory.idproduct = ?2 AND p.idproducthistory.statusvalue = ?3 ")
	List<Product> findAllProductBranchByIdbranchAndIdproduct(Branch idbranch, Product idproduct,
			Integer status, Pageable pagination);
	
	@Query("SELECT SUM(p.idproducthistory.saleprice)/12 FROM ProductBranch p WHERE p.idbranch = ?1 "
			+ "AND p.idproducthistory.idproduct = ?2 AND p.statusvalue = ?3 AND p.saledate BETWEEN ?4 AND ?5 ")
	BigDecimal getMonthlyAverageSales(Branch idbranch, Product idproduct, Integer status, String initDate, String endDate);
	
	@Query("SELECT SUM(p.idproducthistory.saleprice)/365 FROM ProductBranch p WHERE p.idbranch = ?1 "
			+ "AND p.idproducthistory.idproduct = ?2 AND p.statusvalue = ?3 AND p.saledate BETWEEN ?4 AND ?5 ")
	BigDecimal getDailyAverageSales(Branch idbranch, Product idproduct, Integer status, String initDate, String endDate);
	
	
}
