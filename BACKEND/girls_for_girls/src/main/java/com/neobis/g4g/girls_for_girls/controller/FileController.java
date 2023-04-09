package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.FileDownloadDTO;
import com.neobis.g4g.girls_for_girls.data.entity.File;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MENTOR')")
    @Operation(summary = "Загрузка файла", tags = "Файлы")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content =
    @Content(mediaType = "multipart/from-data", schema =
    @Schema(implementation = File.class)), description = "Тело запроса должно содержать объект JSON, представляющий создание файла.")
    public ResponseEntity<?> upload(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "productId", required = false) Integer productId,
            @RequestParam(value = "articleId", required = false) Integer articleId,
            @AuthenticationPrincipal User authUser) {
        return fileService.upload(files, productId, articleId, authUser);
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/download")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MENTOR')")
    @Operation(summary = "Выгрузка файла", tags = "Файлы")
    public ResponseEntity<?> download(@Parameter(description = "Файл код")
                                      @PathParam("fileCode") String fileCode) {
        return fileService.download(fileCode);
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/download/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MENTOR')")
    @Operation(summary = "Выгрзука файла по Id продукта", tags = "Файлы")
    public ResponseEntity<?> downloadByProductId(@Parameter(description = "id product")
                                             @PathVariable("productId") Integer productId) throws IOException {
        return fileService.downloadByProductId(productId);
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/download/{articleId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MENTOR')")
    @Operation(summary = "Выгрзука файла по Id статьи", tags = "Файлы")
    public ResponseEntity<?> downloadByArticleId(@Parameter(description = "id article")
                                             @PathVariable("articleId") Integer articleId) throws IOException {
        return fileService.downloadByArticleId(articleId);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MENTOR')")
    @Operation(summary = "Удаление файла", tags = "Файлы")
    public ResponseEntity<?> delete(@RequestBody() FileDownloadDTO body) {
        return fileService.delete(body);
    }
}
