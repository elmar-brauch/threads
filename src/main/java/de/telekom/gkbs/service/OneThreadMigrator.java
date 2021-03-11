package de.telekom.gkbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.telekom.gkbs.model.ShoppingCart;
import de.telekom.gkbs.persistence.ShoppingCartRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OneThreadMigrator {

	@Autowired private ShoppingCartRepository dataRepo;
    
    public void migrateCarts() {
        log.info("Read all carts with single request...");
        long start = System.currentTimeMillis();
        Iterable<ShoppingCart> carts = dataRepo.findAll();
        for (ShoppingCart cart : carts) {
        	migrateSingleCart(cart);
        }
        log.info("Migration completed in {} seconds!", ((System.currentTimeMillis() - start)/1000));
    }
    
    private void migrateSingleCart(ShoppingCart cart) {
    	log.info("Cart with id {} is in progress...", cart.getId());
        // Doing complicated and time consuming migration logic.
    	// Simulated by save method.
    	dataRepo.save(cart);
    }

}
