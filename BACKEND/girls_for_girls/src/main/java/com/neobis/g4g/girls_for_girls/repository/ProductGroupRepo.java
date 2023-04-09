package com.neobis.g4g.girls_for_girls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductGroupRepo extends JpaRepository<ProductGroup, Long> {

    Optional<ProductGroup> findByTitle(String title);

}
