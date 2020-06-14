package com.calpullix.service.product.list.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.product.list.model.ProductDetailRequestDTO;
import com.calpullix.service.product.list.model.ProductListRequest;
import com.calpullix.service.product.list.service.ProductService;
import com.calpullix.service.product.list.util.AbstractWrapper;
import com.calpullix.service.product.list.util.ValidationHandler;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ProductListHandler {

	@Value("${app.message-error-location-body}")
	private String messageErrorLocationBody;

	@Autowired
	private ValidationHandler validationHandler;

	@Autowired
	private ProductService productService;

	@Timed(value = "calpullix.service.product.list.metrics", description = "Retriving products by branch")
	public Mono<ServerResponse> getProductDetailList(ServerRequest serverRequest) {
		log.info(":: Get Product List Handler {} ", serverRequest);
		return validationHandler
				.handle(input -> input.flatMap(request -> AbstractWrapper.async(() -> productService.getProductByFilters(request)))
						.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
						serverRequest, ProductListRequest.class)
				.switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}

	@Timed(value = "calpullix.service.product.detail.metrics", description = "Retriving product detail")
	public Mono<ServerResponse> getProductDetail(ServerRequest serverRequest) {
		log.info(":: Get Product Detail Handler {} ", serverRequest);
		return validationHandler
				.handle(input -> input
						.flatMap(request -> AbstractWrapper.async(() -> productService.getProductDetail(request)))
						.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
						serverRequest, ProductDetailRequestDTO.class)
				.switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}

	
	@Timed(value = "calpullix.service.product.list.metrics", description = "Retriving product list")
	public Mono<ServerResponse> getProductList(ServerRequest serverRequest) {
		log.info(":: Product List Handler {} ", serverRequest);
		return validationHandler
		.handle(input -> input
				.flatMap(request -> AbstractWrapper.async(() -> {
				    log.info(":: Request product list ", request);
					return productService.getProductList(request);
				}))
				.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
				serverRequest, ProductDetailRequestDTO.class)
		.switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}
	

	@Timed(value = "calpullix.service.product.datasheet.metrics", description = "Retriving product data sheet")
	public Mono<ServerResponse> getDataSheetByProduct(ServerRequest serverRequest) {
		log.info(":: Product Data Sheet Handler {} ", serverRequest);
		return validationHandler
		.handle(input -> input
				.flatMap(request -> AbstractWrapper.async(() -> {
				    log.info(":: Request data sheet ", request);
					return productService.getDataSheetDetail(request);
				}))
				.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
				serverRequest, ProductDetailRequestDTO.class)
		.switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}
	
	
	@Timed(value = "calpullix.service.save.product.metrics", description = "Saving product")
	public Mono<ServerResponse> saveProduct(ServerRequest serverRequest) {
		log.info(":: Save Product Handler {} ", serverRequest);
		return AbstractWrapper.async(() -> productService.saveImage()).flatMap(response -> ServerResponse.ok().build());
	}
	
}
