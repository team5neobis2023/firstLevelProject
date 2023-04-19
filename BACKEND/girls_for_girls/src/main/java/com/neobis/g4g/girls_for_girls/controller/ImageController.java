package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/image")
@Tag(
        name = "Контроллер для работы фотографиями",
        description = "В этом контроллере вы сможете добавлять фотографии"
)
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/upload/product/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Добавление фотографии к продукту по айди",
            tags = "Фото"
    )
    public ResponseEntity<String> saveProductImage(@PathVariable("productId") Long productId,
                                                  @RequestPart MultipartFile file) throws IOException {
        return imageService.saveForProduct(productId, file);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/upload/article/{articleId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Добавление фотографии к статье по айди",
            tags = "Фото"
    )
    public ResponseEntity<String> saveArticleImage(@PathVariable("articleId") Long articleId,
                                                   @RequestPart MultipartFile file) throws IOException {
        return imageService.saveForArticle(articleId, file);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/upload/speaker/{speakerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Добавление фотографии спикера по айди",
            tags = "Фото"
    )
    public ResponseEntity<String> saveSpeakerImage(@PathVariable("speakerId") Long speakerId,
                                                   @RequestPart MultipartFile file) throws IOException {
        return imageService.saveForSpeaker(speakerId, file);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/upload/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Добавление фотографии профиля",
            tags = "Фото"
    )
    public ResponseEntity<String> saveProductImage(@RequestPart MultipartFile file) throws IOException {
        return imageService.saveForUser(file);
    }
}
