package de.telekom.gkbs.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.telekom.gkbs.model.ShoppingCart;

@Primary
@Repository
public class ShoppingCartRepository implements CrudRepository<ShoppingCart, String> {
	
	public static final int TABLE_SIZE = 100;
	
	@Autowired protected DatabaseSimulator dbSimulator;
	
	@Override
	public Iterable<ShoppingCart> findAll() {
		return dbSimulator.generateCarts(TABLE_SIZE);
	}
	
	@Override
	public <S extends ShoppingCart> S save(S entity) {
		dbSimulator.fakeDatabaseWriteOperation();
		return entity;
	}

	// All methods below are just auto-generated stubs, which are not needed for this demo.
	
	@Override
	public <S extends ShoppingCart> Iterable<S> saveAll(Iterable<S> entities) {
		return null;
	}

	@Override
	public Optional<ShoppingCart> findById(String id) {
		return Optional.empty();
	}

	@Override
	public boolean existsById(String id) {
		return false;
	}

	@Override
	public Iterable<ShoppingCart> findAllById(Iterable<String> ids) {
		return null;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void deleteById(String id) {}

	@Override
	public void delete(ShoppingCart entity) {}

	@Override
	public void deleteAll(Iterable<? extends ShoppingCart> entities) {}

	@Override
	public void deleteAll() {}

}
