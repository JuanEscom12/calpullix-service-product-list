package com.calpullix.service.product.list.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class ProductRecomendationProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Integer id;

	@Basic
	@Column(name = "idprofile")
	private Integer idprofilevalue;
	
	@Transient
	private CustomerProfile idprofile;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idproduct", referencedColumnName = "id")
	private Product idproduct;
	
	private String date;
	
	private Boolean isactive;
	
	@PostLoad
    void fillTransient() {
        if (idprofilevalue > 0) {
            this.idprofile = CustomerProfile.of(idprofilevalue);
        }
    }
 
    @PrePersist
    void fillPersistent() {
        if (BooleanUtils.negate(idprofile == null)) {
            this.idprofilevalue = idprofile.getId();
        }
    }


}
