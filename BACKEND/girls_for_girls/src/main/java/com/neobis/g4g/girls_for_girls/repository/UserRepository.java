package com.neobis.g4g.girls_for_girls.repository;

import com.neobis.g4g.girls_for_girls.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByEmail(String EMAIL);

    UserEntity findById(int id);

    Optional<UserEntity> findByEmail(String email);
}
