package com.neobis.g4g.girls_for_girls.repository;

import com.neobis.g4g.girls_for_girls.data.entity.Basket;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByUser(User user);

    Optional<Basket> findByProductIdAndUser(long id, User user);

    boolean existsByProductIdAndUser(long productId, User currentUser);
}
