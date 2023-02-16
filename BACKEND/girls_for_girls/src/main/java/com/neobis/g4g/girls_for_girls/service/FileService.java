package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.FileDownloadDTO;
import com.neobis.g4g.girls_for_girls.data.entity.FileEntity;
import com.neobis.g4g.girls_for_girls.data.entity.UserEntity;
import com.neobis.g4g.girls_for_girls.repository.FileRepository;
import com.neobis.g4g.girls_for_girls.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    boolean isErrorOccurred = false;

    public ResponseEntity<?> upload(
            MultipartFile[] files,
            Integer productId,
            Integer articelId,
            UserEntity authUser) {
        List<FileEntity> fileEntities = new ArrayList<>();

        Arrays.stream(files)
                .takeWhile(n -> !isErrorOccurred)
                .forEach(file -> {
                    String name = StringUtils.cleanPath(file.getOriginalFilename());
                    String code;

                    try {
                        code = FileUtil.save(name, file);
                    } catch (IOException ioe) {
                        isErrorOccurred = true;
                        return;
                    }

                    FileEntity fileEntity = new FileEntity();

                    fileEntity.setName(name);
                    if(productId != null) {
                        fileEntity.setProductId(productId);
                    }
                    if(articelId != null){
                        fileEntity.setArticleId(articelId);
                    }
                    fileEntity.setFileCode(code);
                    fileEntity.setUserId(authUser.getId());

                    fileEntities.add(fileEntity);
                });

        if (isErrorOccurred) {
            isErrorOccurred = false;
            return new ResponseEntity<>("Files uploading error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<FileEntity> savedFiles = fileRepository.saveAll(fileEntities);
        isErrorOccurred = false;

        return new ResponseEntity<>(savedFiles, HttpStatus.OK);
    }

    public ResponseEntity<?> download(String fileCode) {
        Resource resource;

        try {
            resource = FileUtil.getFileAsResource(fileCode);
        } catch (IOException ioe) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = resource.getFilename();

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    public ResponseEntity<?> downloadByProductId(Integer productId) throws IOException {
        List<FileEntity> fileEntities = fileRepository.findByProductIdAndIsDeletedFalse(productId);
        List<Resource> resources = new ArrayList<>();

        fileEntities.forEach((fileEntity) -> {
            Resource resource;

            try {
                resource = FileUtil.getFileAsResource(fileEntity.getFileCode());
                resources.add(resource);
            } catch (Exception e) {
                isErrorOccurred = true;
            }
        });

        if (isErrorOccurred) {
            isErrorOccurred = false;
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resources);
    }

    public ResponseEntity<?> downloadByArticleId(Integer articleId) throws IOException {
        List<FileEntity> fileEntities = fileRepository.findByArticleIdAndIsDeletedFalse(articleId);
        List<Resource> resources = new ArrayList<>();

        fileEntities.forEach((fileEntity) -> {
            Resource resource;

            try {
                resource = FileUtil.getFileAsResource(fileEntity.getFileCode());
                resources.add(resource);
            } catch (Exception e) {
                isErrorOccurred = true;
            }
        });

        if (isErrorOccurred) {
            isErrorOccurred = false;
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resources);
    }

    public ResponseEntity<?> delete(FileDownloadDTO body) {
        FileEntity fileEntity = fileRepository.findByFileCode(body.fileCode);

        fileEntity.setIsDeleted(true);

        fileRepository.save(fileEntity);

        return ResponseEntity.status(HttpStatus.OK).body("Deleted!");
    }

}