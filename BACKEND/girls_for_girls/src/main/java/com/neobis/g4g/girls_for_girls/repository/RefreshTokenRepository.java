package com.neobis.g4g.girls_for_girls.repository;

import com.neobis.g4g.girls_for_girls.data.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {
    RefreshTokenEntity findByToken(String token);
}
