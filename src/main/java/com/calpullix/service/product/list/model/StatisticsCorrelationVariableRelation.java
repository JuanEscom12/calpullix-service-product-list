package com.calpullix.service.product.list.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class StatisticsCorrelationVariableRelation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer id;

	private String verticalvarname;
	
	private String horizontalvarnameone;
	
	private String horizontalvarnametwo;
	
	private String horizontalvarnamethree;
	
	private String horizontalvarnamefour;
	
}
