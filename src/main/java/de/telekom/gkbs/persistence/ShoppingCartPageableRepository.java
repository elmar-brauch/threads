package de.telekom.gkbs.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import de.telekom.gkbs.model.ShoppingCart;

@Repository
public class ShoppingCartPageableRepository extends ShoppingCartRepository implements PagingAndSortingRepository<ShoppingCart, String> {

	@Override
	public Page<ShoppingCart> findAll(Pageable pageable) {
		var carts = dbSimulator.generateCarts(pageable.getOffset(), pageable.getPageSize());
		return new PageImpl<>(carts, pageable, TABLE_SIZE);
	}
	
	// All methods below are just auto-generated stubs, which are not needed for this demo.
	
	@Override
	public Iterable<ShoppingCart> findAll(Sort sort) {
		return null;
	}

}
