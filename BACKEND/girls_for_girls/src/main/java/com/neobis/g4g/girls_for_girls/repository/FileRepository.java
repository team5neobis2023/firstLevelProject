package com.neobis.g4g.girls_for_girls.repository;

import com.neobis.g4g.girls_for_girls.data.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findByProductIdAndIsDeletedFalse(int id);

    List<File> findByArticleIdAndIsDeletedFalse(int id);

    File findByFileCode(String filecode);
}
