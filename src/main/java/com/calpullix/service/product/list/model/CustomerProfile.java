package com.calpullix.service.product.list.model;

import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum CustomerProfile {

	PREMIUM(1, "Premium"), VIP(2, "VIP"), AVERAGE(3, "Promedio"), 
	LOW(4, "Minimo"), OCASSIONAL(5, "Ocasional");
	
	private Integer id;
	
	private String description;
	
	private CustomerProfile(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public static CustomerProfile of(int id) {
		return Stream.of(CustomerProfile.values())
			.filter(p -> p.getId() == id)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}

}
