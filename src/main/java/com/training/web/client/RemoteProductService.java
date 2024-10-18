package com.training.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("product-app")
public interface RemoteProductService {
	
	@GetMapping("/products/{id}")
	public Product getProductById(@PathVariable("id") int id);

}
