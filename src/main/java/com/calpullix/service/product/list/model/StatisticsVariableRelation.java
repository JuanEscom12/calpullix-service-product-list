package com.calpullix.service.product.list.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class StatisticsVariableRelation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer id;

	private String statisticvar;

	private String varvalueone;
	
	private String varvaluetwo;
	
	private String varvaluethree;
	
	private String varvaluefour;
	
	@OneToMany(
	        mappedBy = "variablerelations",
	        orphanRemoval = true,
	        fetch = FetchType.LAZY)
	private List<Statistics> statistics;
	
	
}
