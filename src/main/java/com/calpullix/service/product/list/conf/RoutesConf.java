package com.calpullix.service.product.list.conf;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.product.list.handler.ProductListHandler;

@Configuration
public class RoutesConf {

	@Value("${app.path-get-product-list}")
	private String pathGetProductList;
	
	@Value("${app.path-get-product-detail}")
	private String pathProductDetail;
	
	
	@Value("${app.path-save-product}")
	private String pathSaveProduct;
	
	@Value("${app.path-get-product-name-list}")
	private String pathProductNameList;
	
	@Value("${app.path-get-product-data-sheet}")
	private String pathGetProductDataSheet;
	

	@Bean
	public RouterFunction<ServerResponse> routesLogin(ProductListHandler loginHandler) {
		return route(POST(pathGetProductList), loginHandler::getProductDetailList)
				.and(route(POST(pathProductDetail), loginHandler::getProductDetail))
				.and(route(POST(pathProductNameList), loginHandler::getProductList))
				.and(route(POST(pathGetProductDataSheet), loginHandler::getDataSheetByProduct))
				.and(route(POST(pathSaveProduct), loginHandler::saveProduct));
	}
	
}
