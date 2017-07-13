package com.spring.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository
    extends JpaRepository<Product, Integer> {   
	List<Product> findByName(String find);
}
