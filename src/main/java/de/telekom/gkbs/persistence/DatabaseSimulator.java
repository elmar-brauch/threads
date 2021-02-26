package de.telekom.gkbs.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.telekom.gkbs.model.ShoppingCart;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DatabaseSimulator {
	
	public static final long FAKED_DB_WRITE_TIME_IN_MS = 500;
	public static final long FAKED_DB_READ_TIME_IN_MS = 200;
	
	public void fakeDatabaseWriteOperation() {
		fakeDatabaseInteractionInMs(FAKED_DB_WRITE_TIME_IN_MS);
	}
	
	public List<ShoppingCart> generateCarts(int numberOfCartsToBeGenerated) {
		return generateCarts(0, numberOfCartsToBeGenerated);
	}

	public List<ShoppingCart> generateCarts(long offset, int pageSize) {
		fakeDatabaseInteractionInMs(FAKED_DB_READ_TIME_IN_MS * pageSize);
		List<ShoppingCart> result = new ArrayList<>(pageSize);
		for (long i = offset; i < offset + pageSize; i++)
			result.add(new ShoppingCart(i + ""));
		return result;
	}
	
	private void fakeDatabaseInteractionInMs(long sleepTimeInMs) {
		try {
			Thread.sleep(sleepTimeInMs);
		} catch (InterruptedException e) {
			log.warn("Interrupted!", e);
			Thread.currentThread().interrupt();
		}
	}

}
