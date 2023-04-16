package com.neobis.g4g.girls_for_girls.repository;

import com.neobis.g4g.girls_for_girls.data.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByProductIdAndIsDeletedFalse(int id);
    Optional<File> findByProductId(long id);

    List<File> findByArticleIdAndIsDeletedFalse(int id);

    File findByFileCode(String filecode);
}
