package com.neobis.g4g.girls_for_girls.repository;

import com.neobis.g4g.girls_for_girls.data.entity.TrainingApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingApplicationRepository extends JpaRepository<TrainingApplication, Long> {
    boolean existsByEmail(String email);
    List<TrainingApplication> findAllByTrainingId(long id);
    boolean existsByEmailAndTrainingId(String email, long id);
}
