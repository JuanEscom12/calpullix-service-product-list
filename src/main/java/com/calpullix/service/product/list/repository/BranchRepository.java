package com.calpullix.service.product.list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calpullix.service.product.list.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

}
