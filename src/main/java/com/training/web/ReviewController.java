package com.training.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.dal.ReviewRepository;
import com.training.domain.Review;
import com.training.web.client.Product;
import com.training.web.client.RemoteProductService;

@RestController
public class ReviewController {
	
	@Autowired
	ReviewRepository repo;
	
	@Autowired
	RemoteProductService productService;

//	@Value("${product.base.url}")
	
	
	@PostMapping("/reviews")
	public ResponseEntity addReview(@RequestBody Review toBeAdded) {
		int pid = toBeAdded.getPid();
		
		try {
			Product found = productService.getProductById(pid);
			
			Review added = repo.save(toBeAdded);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("/reviews/"+added.getId()));
			return new ResponseEntity(added, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity("Product with id "+pid+" not found!",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/reviews") // meant to be used as /reviews?pid=123
	public List<Review> getReviewsForProduct(@RequestParam("pid") int productId){
		return repo.findByPid(productId);
	}
}
