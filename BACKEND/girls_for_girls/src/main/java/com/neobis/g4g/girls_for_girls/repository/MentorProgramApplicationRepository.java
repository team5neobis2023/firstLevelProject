package com.neobis.g4g.girls_for_girls.repository;

import com.neobis.g4g.girls_for_girls.data.entity.MentorProgramApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorProgramApplicationRepository extends JpaRepository<MentorProgramApplication, Long> {
    List<MentorProgramApplication> findAllByMentorProgramId(long id);

    boolean existsByEmailAndMentorProgramId(String email, long mentorProgramId);
}
