package com.neobis.g4g.girls_for_girls.repository;

import com.neobis.g4g.girls_for_girls.data.entity.ProductGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductGroupRepo extends JpaRepository<ProductGroupEntity, Long> {

    Optional<ProductGroupEntity> findByTitle(String title);

}
