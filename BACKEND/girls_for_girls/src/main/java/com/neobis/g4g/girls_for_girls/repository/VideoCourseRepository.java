package com.neobis.g4g.girls_for_girls.repository;

import com.neobis.g4g.girls_for_girls.data.entity.VideoCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoCourseRepository extends JpaRepository<VideoCourse, Long> {
    List<VideoCourse> findVideoCoursesByVideoCourseCategoryId(Long id);
}
