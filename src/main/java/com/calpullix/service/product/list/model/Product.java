package com.calpullix.service.product.list.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer id;
	
	private String name;
	
	private String description;
	
	@Basic
	@Column(name = "brand")
	private Integer brandvalue;
	
	@Transient
	private Brand brand;
	
	private BigDecimal weight;
	
	@Basic
	@Column(name = "weightunit")
	private Integer weightunitvalue;
	
	@Transient
	private WeightUnit weightunit;
	
	@Column(name = "individualpackaging")
	private Boolean individualPackaging;
	
	@Column(name = "cofeprispermission")
	private Boolean cofeprisPermission;
	
	@Column(name = "fragilematerial")
	private Boolean fragileMaterial;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider", referencedColumnName = "id")
	private Provider provider;
	
	@Basic
	@Column(name = "category")
	private Integer categoryvalue;
	
	@Transient
	private ProductCategories category;
	
	private String measurements;

	
	@Basic
	@Column(name = "classification")
	private Integer classificationvalue;
	
	@Transient
	private ProductClassification classification;

	@Lob
	private byte[] image;
	
	@OneToMany(
	        mappedBy = "idproduct",
	        orphanRemoval = true,
	        fetch = FetchType.LAZY)
	private List<DataSheet> datasheet;
	
	@OneToMany(
	        mappedBy = "idproduct",
	        orphanRemoval = true,
	        fetch = FetchType.LAZY)
	private List<Purchaseorder> purchaseOrder;
	
	@OneToMany(
	        mappedBy = "idproduct",
	        orphanRemoval = true,
	        fetch = FetchType.LAZY)
	private List<Promotions> promotions;
	
	
	@OneToMany(
	        mappedBy = "idproduct",
	        orphanRemoval = true,
	        fetch = FetchType.LAZY)
	private List<ProductRecomendationProfile> productrecomendationprofile;
	
	@OneToMany(
	        mappedBy = "idproduct",
	        orphanRemoval = true,
	        fetch = FetchType.LAZY)
	private List<Sales> sales;
	
	@OneToMany(
	        mappedBy = "idproduct",
	        orphanRemoval = true,
	        fetch = FetchType.LAZY)
	private List<Statistics> statistics;
	
	@OneToMany(
	        mappedBy = "idproduct",
	        orphanRemoval = true,
	        fetch = FetchType.LAZY)
	private List<StatisticsCorrelation> statisticsCorrelation;
	
	@OneToMany(
	        mappedBy = "idproduct",
	        orphanRemoval = true,
	        fetch = FetchType.LAZY)
	private List<StatisticsAnova> statisticsanova;
	
	@OneToMany(
	        mappedBy = "idproduct",
	        orphanRemoval = true,
	        fetch = FetchType.LAZY)
	private List<ProductHistory> productHistory;
	
	@PostLoad
    void fillTransient() {
        if (brandvalue > 0) {
            this.brand = Brand.of(brandvalue);
        }
        if (weightunitvalue > 0) {
            this.weightunit = WeightUnit.of(weightunitvalue);
        }
        if (categoryvalue > 0) {
            this.category = ProductCategories.of(categoryvalue);
        }
        if (BooleanUtils.negate(classificationvalue == null) && classificationvalue > 0) {
            this.classification = ProductClassification.of(classificationvalue);
        }
    }
 
    @PrePersist
    void fillPersistent() {
        if (BooleanUtils.negate(brand == null)) {
            this.brandvalue = brand.getId();
        }
        if (BooleanUtils.negate(weightunit == null)) {
            this.weightunitvalue = weightunit.getId();
        }
        if (BooleanUtils.negate(category == null)) {
            this.categoryvalue = category.getId();
        }
        if (BooleanUtils.negate(classification == null)) {
            this.classificationvalue = classification.getId();
        }
    }

	@Override
	public String toString() {
		return "Product [id=" + id + "]";
	}
	
	
}

