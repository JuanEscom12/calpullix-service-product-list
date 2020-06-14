package com.calpullix.service.product.list.model;

import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum PurchaseOrderStatus {

	CREATED(1, "Creada"), ON_PROCESS(2, "En proceso"), 
	CANCELED(3, "Cancelada");
	
	private Integer id;
	
	private String description;
	
	private PurchaseOrderStatus(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public static PurchaseOrderStatus of(int id) {
		return Stream.of(PurchaseOrderStatus.values())
			.filter(p -> p.getId() == id)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}

}
