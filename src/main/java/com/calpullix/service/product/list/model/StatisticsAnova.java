package com.calpullix.service.product.list.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class StatisticsAnova {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idbranch", referencedColumnName = "id")
	private Branch idbranch;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idproduct", referencedColumnName = "id")
	private Product idproduct;
	
	private Integer year;
	
	private Integer month;
	
	private String varindname;
	
	private String vardepname;
	
	private String subvalueindvar;
	
	private String ftestScore;
	
	private String pvalue;


}
