package com.neobis.g4g.girls_for_girls.repository;

import com.neobis.g4g.girls_for_girls.data.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    List<FileEntity> findByProductIdAndIsDeletedFalse(int id);

    List<FileEntity> findByArticleIdAndIsDeletedFalse(int id);

    FileEntity findByFileCode(String filecode);
}
