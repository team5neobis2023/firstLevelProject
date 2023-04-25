package com.neobis.g4g.girls_for_girls.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.neobis.g4g.girls_for_girls.data.entity.*;
import com.neobis.g4g.girls_for_girls.exception.FileEmptyException;
import com.neobis.g4g.girls_for_girls.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final UserRepository userRepository;
    private final ProductRepo productRepo;
    private final ArticleRepository articleRepository;
    private final MentorProgramRepository mentorProgramRepository;
    private final TrainingRepository trainingRepository;
    private final SpeakerRepository speakerRepository;
    private final MentorProgramApplicationRepository mentorProgramApplicationRepository;

    public ResponseEntity<String> saveForProduct(Long productId, MultipartFile file) throws IOException {
        if(productRepo.existsById(productId)){
            Product product = productRepo.findById(productId).get();
            product.setImage_url(saveImage(file));
            productRepo.save(product);
            return ResponseEntity.ok("Image was saved");
        }else{
            return ResponseEntity.badRequest().body("Product with id " + productId + " wasn't found");
        }
    }

    public ResponseEntity<String> saveForUser(MultipartFile file) throws IOException {
        User user = getAuthentication();
        user.setImage_url(saveImage(file));
        userRepository.save(user);
        return ResponseEntity.ok("Image was saved");
    }

    public ResponseEntity<String> saveForArticle(Long articleId, MultipartFile file) throws IOException {
        if(articleRepository.existsById(articleId)){
            Article article = articleRepository.findById(articleId).get();
            article.setImage_url(saveImage(file));
            articleRepository.save(article);
            return ResponseEntity.ok("Image was saved");
        }else{
            return ResponseEntity.badRequest().body("Article with id " + articleId + " wasn't found");
        }
    }

    public ResponseEntity<String> saveForSpeaker(Long speakerId, MultipartFile file) throws IOException {
        if(speakerRepository.existsById(speakerId)){
            Speaker speaker = speakerRepository.findById(speakerId).get();
            speaker.setImage_url(saveImage(file));
            speakerRepository.save(speaker);
            return ResponseEntity.ok("Image was saved");
        }else{
            return ResponseEntity.badRequest().body("Speaker with id " + speakerId + " wasn't found");
        }
    }

    public ResponseEntity<String> saveForMentorProgram(Long id, MultipartFile file) throws IOException {
        if(mentorProgramRepository.existsById(id)){
            MentorProgram mentorProgram = mentorProgramRepository.findById(id).get();
            mentorProgram.setImage_url(saveImage(file));
            mentorProgramRepository.save(mentorProgram);
            return ResponseEntity.ok("Image was saved");
        }else{
            return ResponseEntity.badRequest().body("Mentor program with id " + id + " wasn't found");
        }
    }

    public ResponseEntity<String> saveForTraining(Long id, MultipartFile file) throws IOException {
        if(trainingRepository.existsById(id)){
            Training training = trainingRepository.findById(id).get();
            training.setImage_url(saveImage(file));
            trainingRepository.save(training);
            return ResponseEntity.ok("Image was saved");
        }else{
            return ResponseEntity.badRequest().body("Training with id " + id + " wasn't found");
        }
    }

    public ResponseEntity<String> saveResume(Long id, MultipartFile file) throws IOException {
        MentorProgramApplication mentorProgramApplication = mentorProgramApplicationRepository.findById(id).get();
        mentorProgramApplication.setResumeUrl(saveImage(file));
        mentorProgramApplicationRepository.save(mentorProgramApplication);
        return ResponseEntity.ok("Image was saved");
    }


    public String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new FileEmptyException("File is empty");
        }

        final String urlKey = "cloudinary://753949556892917:SCszCjA1duCgeAaMxDP-7Qq3dP8@dja0nqat2";

        File saveFile = Files.createTempFile(
                        System.currentTimeMillis() + "",
                        Objects.requireNonNull
                                        (file.getOriginalFilename(), "File must have an extension")
                                .substring(file.getOriginalFilename().lastIndexOf("."))
                )
                .toFile();

        file.transferTo(saveFile);

        Cloudinary cloudinary = new Cloudinary((urlKey));

        Map upload = cloudinary.uploader().upload(saveFile, ObjectUtils.emptyMap());

        return (String) upload.get("url");
    }

    public User getAuthentication(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(((UserDetails)principal).getUsername()).get();
    }
}
