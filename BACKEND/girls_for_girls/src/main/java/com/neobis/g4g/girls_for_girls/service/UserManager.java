package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ChangePassDTO;
import com.neobis.g4g.girls_for_girls.data.dto.UserDTO;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.data.entity.UserGroup;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.text.MessageFormat;
import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.ArticleDTO.toArticleDTO;
import static com.neobis.g4g.girls_for_girls.data.dto.NotificationDTO.notificationToNotificationDtoList;

@Service
public class UserManager implements UserDetailsManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserGroupRepository userGroupRepository;
    private final RegionRepository regionRepository;
    private final ArticleRepository articleRepository;
    private final NotificationRepo notificationRepo;

    @Autowired
    public UserManager(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, UserGroupRepository userGroupRepository, RegionRepository regionRepository, ArticleRepository articleRepository, NotificationRepo notificationRepo) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userGroupRepository = userGroupRepository;
        this.regionRepository = regionRepository;
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

    public ResponseEntity<?> changePassword(ChangePassDTO changePassDTO,
                                            BindingResult bindingResult,
                                            User user){
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(changePassDTO.getNewPassword().equals(changePassDTO.getConfirmNewPassword())) {
            if (passwordEncoder.matches(changePassDTO.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(changePassDTO.getNewPassword()));
                userRepository.save(user);
                return ResponseEntity.ok("Password was updated");
            }else{
                return new ResponseEntity<>("Write old password correctly", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("New password doesn't match with new password confirmation", HttpStatus.BAD_REQUEST);
        }
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

                user.setEmail(userDTO.getEmail());
                user.setFirstName(userDTO.getFirstName());
                user.setRole(role);
                user.setLastName(userDTO.getLastName());
                user.setPhoneNumber(userDTO.getPhoneNumber());
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

    private StringBuilder getErrorMsg(BindingResult bindingResult){
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        }
        return errorMsg;
    }
}
