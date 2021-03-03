package de.telekom.gkbs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.telekom.gkbs.model.ShoppingCart;
import de.telekom.gkbs.persistence.ShoppingCartPageableRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MultiThreadMigrator {
    
    private final ExecutorService threadPool = Executors.newFixedThreadPool(25);
    
    @Autowired private ShoppingCartPageableRepository dataRepo;
    private static final int PAGE_SIZE = 10; 
       
    public void migrateCarts() {
        log.info("Read all carts with pagination...");
        long start = System.currentTimeMillis();
        List<CompletableFuture<Void>> threadList = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
    	while(true) {
    		log.info("Request page {}.", pageable.getPageNumber());
    		var page = dataRepo.findAll(pageable);
    		threadList.add(CompletableFuture.runAsync(() -> migrateCartPage(page), threadPool));
    		if (page.hasNext())
    			pageable = page.getPageable().next();
    		else
    			break;
    	}
    	log.info("All pages passed to threads.");
    	CompletableFuture.allOf(threadList.toArray(CompletableFuture[]::new)).join();
    	log.info("All Migration threads completed in {} seconds!", ((System.currentTimeMillis() - start)/1000));
    }
    
    private void migrateCartPage(Page<ShoppingCart> page) {
    	try {
	    	for (ShoppingCart shoppingCart : page) {
	        	log.info("Cart with id {} is in progress...", shoppingCart.getId());
	            // Doing complicated and time consuming migration logic.
	        	// Simulated by save method.
	        	dataRepo.save(shoppingCart);
	        }
	    	log.info("Successful migration of CART-page {} of {}", page.getNumber(), page.getTotalPages());
    	} catch (Exception ex) {
    		log.error("Error during migration of CART-page " + page.getNumber(), ex);
    	}	
    }
}
