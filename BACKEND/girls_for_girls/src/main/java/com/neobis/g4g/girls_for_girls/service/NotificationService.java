package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.NotificationDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Notification;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.repository.NotificationRepo;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepo notificationRepo;
    private final UserRepository userRepository;

    public List<NotificationDTO> getAllNotifications() {
        return NotificationDTO.notificationToNotificationDtoList(notificationRepo.findAll());
    }

    public ResponseEntity<?> getNotificationsByUserID(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            if (notificationRepo.findAllByUserId(userId).isEmpty()) {
                return new ResponseEntity<String>("There are no notifications for this user by id: " + userId, HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.ok(NotificationDTO.notificationToNotificationDtoList(notificationRepo.findAllByUserId(userId)));
            }
        } else {
            return new ResponseEntity<>("User with this id: " + userId + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> addNotification(NotificationDTO notificationDTO) {
        try {
            Notification notification = new Notification();
            notification.setUser(userRepository.findById(notificationDTO.getUserId()).get());
            notification.setMessage(notificationDTO.getMessage());
            notificationRepo.save(notification);
            return ResponseEntity.ok("Notification is created");
        } catch (Exception e) {
            return new ResponseEntity<>("Notification isn't created", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateNotificationById(Long id, NotificationDTO notificationDTO) {
        return notificationRepo.findById(id)
                .map(notification -> {
                    notification.setMessage(notificationDTO.getMessage());
                    notification.setUser(userRepository.findById(notificationDTO.getUserId()).get());
                    notificationRepo.save(notification);
                    return ResponseEntity.ok("Notification with this id: " + id + " updated");
                }).orElse(new ResponseEntity<>("Notification with this id: " + id + " not found", HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteNotificationById(Long id) {
        if (notificationRepo.existsById(id)) {
            notificationRepo.deleteById(id);
            return ResponseEntity.ok("Notification is deleted");
        } else {
            return new ResponseEntity<String>("There is no such notification", HttpStatus.NOT_FOUND);
        }
    }
}
