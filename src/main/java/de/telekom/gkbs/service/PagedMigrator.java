package de.telekom.gkbs.service;

import java.util.*;
import java.util.concurrent.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import de.telekom.gkbs.model.ShoppingCart;
import de.telekom.gkbs.persistence.ShoppingCartPageableRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PagedMigrator {
    
    @Autowired private ShoppingCartPageableRepository dataRepo;
    
    private static final int PAGE_SIZE = 10; 
       
    public void migrateCarts() {
        log.info("Read all carts with pagination...");
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        do {
    		log.info("Request page {}.", pageable.getPageNumber());
    		var page = dataRepo.findAll(pageable);
    		migrateCartPage(page);
    		pageable = getNextPageable(page);
    	} while(pageable != null);
    }
    
    private Pageable getNextPageable(Page<ShoppingCart> currentPage) {
    	if (currentPage.hasNext())
    		return currentPage.getPageable().next();
		return null;
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
