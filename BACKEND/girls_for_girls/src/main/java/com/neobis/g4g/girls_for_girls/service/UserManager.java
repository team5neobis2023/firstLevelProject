package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.UserDTO;
import com.neobis.g4g.girls_for_girls.data.entity.UserEntity;
import com.neobis.g4g.girls_for_girls.data.entity.UserGroupEntity;
import com.neobis.g4g.girls_for_girls.exception.UsernameAlreadyExistException;
import com.neobis.g4g.girls_for_girls.repository.UserGroupRepository;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
@Service
public class UserManager implements UserDetailsManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserGroupRepository userGroupRepository;

    @Autowired
    public UserManager(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, UserGroupRepository userGroupRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userGroupRepository = userGroupRepository;
    }

    @Override
    public void createUser(UserDetails user) {
        boolean isExist = userRepository.existsByEmail(user.getUsername());

        if (isExist) {
            throw new UsernameAlreadyExistException(
                    MessageFormat.format("Email {0} already exist", user.getUsername()));
        }

        ((UserEntity) user).setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save((UserEntity) user);
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

    public ResponseEntity<?> saveUser(UserDTO userDTO, UserEntity userEntity){
        try {

            UserGroupEntity userGroupEntity = userGroupRepository.findById(userDTO.getRole().getId());
            UserEntity newUser = new UserEntity();

            newUser.setEmail(userDTO.getEmail());
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            newUser.setRole(userGroupEntity);
            newUser.setFirstName(userDTO.getFirstName());
            newUser.setLastName(userDTO.getLastName());
            newUser.setPhoneNumber(userDTO.getPhoneNumber());
            newUser.setFile(userDTO.getFile());
            newUser.setPlaceOfBirth(userDTO.getPlaceOfBirth());
            userRepository.save(newUser);

            return ResponseEntity.ok().body(newUser.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    public ResponseEntity<?> updateUser(UserDTO userDTO){
        try {
            boolean exist = userRepository.existsById(userDTO.getId());

            if(exist) {
                UserEntity user = userRepository.findById(userDTO.getId());

                user.setEmail(userDTO.getEmail());
                user.setFirstName(userDTO.getFirstName());
                user.setRole(userDTO.getRole());
                user.setLastName(userDTO.getLastName());
                user.setPhoneNumber(userDTO.getPhoneNumber());
                user.setFile(userDTO.getFile());
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                user.setPlaceOfBirth(userDTO.getPlaceOfBirth());
                userRepository.save(user);

                return ResponseEntity.ok().body(user.getId());
            }
            throw new IllegalStateException("User with id " + userDTO.getId() + " does not exist");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }
}
