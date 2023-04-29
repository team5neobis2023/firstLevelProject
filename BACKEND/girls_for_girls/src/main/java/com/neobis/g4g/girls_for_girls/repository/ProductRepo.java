package com.neobis.g4g.girls_for_girls.repository;

import com.neobis.g4g.girls_for_girls.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<Product> findByTitle (String title);

}
