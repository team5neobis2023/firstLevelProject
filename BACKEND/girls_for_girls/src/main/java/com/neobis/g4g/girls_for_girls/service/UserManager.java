package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.UserDTO;
import com.neobis.g4g.girls_for_girls.data.entity.File;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.data.entity.UserGroup;
import com.neobis.g4g.girls_for_girls.exception.UsernameAlreadyExistException;
import com.neobis.g4g.girls_for_girls.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

import static com.neobis.g4g.girls_for_girls.data.dto.ArticleDTO.toArticleDTO;
import static com.neobis.g4g.girls_for_girls.data.dto.NotificationDTO.notificationToNotificationDtoList;

@Service
public class UserManager implements UserDetailsManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserGroupRepository userGroupRepository;
    private final RegionRepository regionRepository;
    private final FileRepository fileRepository;
    private final ArticleRepository articleRepository;
    private final NotificationRepo notificationRepo;

    @Autowired
    public UserManager(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, UserGroupRepository userGroupRepository, RegionRepository regionRepository, FileRepository fileRepository, ArticleRepository articleRepository, NotificationRepo notificationRepo) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userGroupRepository = userGroupRepository;
        this.regionRepository = regionRepository;
        this.fileRepository = fileRepository;
        this.articleRepository = articleRepository;
        this.notificationRepo = notificationRepo;
    }

    @Override
    public void createUser(UserDetails user) {
        boolean isExist = userRepository.existsByEmail(user.getUsername());

        if (isExist) {
            throw new UsernameAlreadyExistException(
                    MessageFormat.format("Email {0} already exist", user.getUsername()));
        }

        ((User) user).setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteUser(String username) {
        // TODO Auto-generated method stub
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByEmail(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(MessageFormat.format("Email {0} not found", username)));
    }

    public ResponseEntity<?> getAllArticlesByLikedUsersId(long id){
        if(userRepository.existsById(id)){
            return ResponseEntity.ok(toArticleDTO(articleRepository.findArticlesByLikedUsersId(id)));
        }else{
            return new ResponseEntity<>("User with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAllNotificationsByUserId(long id) {
        if(userRepository.existsById(id)){
            return ResponseEntity.ok(notificationToNotificationDtoList(notificationRepo.findAllByUserId(id)));
        }else{
            return new ResponseEntity<>("User with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> saveUser(UserDTO userDTO){
        try {

            UserGroup userGroup = userGroupRepository.findById(3);
            User newUser = new User();

            newUser.setEmail(userDTO.getEmail());
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            newUser.setRole(userGroup);
            newUser.setFirstName(userDTO.getFirstName());
            newUser.setLastName(userDTO.getLastName());
            newUser.setPhoneNumber(userDTO.getPhoneNumber());
            newUser.setRegion(regionRepository.findById(userDTO.getRegion_id()).get());
            userRepository.save(newUser);

            return ResponseEntity.ok().body(newUser.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    public ResponseEntity<?> updateUser(UserDTO userDTO){
        try {
            boolean exist = userRepository.existsByEmail(userDTO.getEmail());

            if(exist) {
                User user = userRepository.findByEmail(userDTO.getEmail()).get();
                UserGroup role = userGroupRepository.findById(userDTO.getRole().getId());
                File file = fileRepository.findById(userDTO.getFile().getId()).get();

                user.setEmail(userDTO.getEmail());
                user.setFirstName(userDTO.getFirstName());
                user.setRole(role);
                user.setLastName(userDTO.getLastName());
                user.setPhoneNumber(userDTO.getPhoneNumber());
                user.setFile(file);
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                user.setRegion(regionRepository.findById(userDTO.getRegion_id()).get());
                userRepository.save(user);

                return ResponseEntity.ok().body(user.getId());
            }
            throw new IllegalStateException("User with email " + userDTO.getEmail() + " does not exist");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }
}
