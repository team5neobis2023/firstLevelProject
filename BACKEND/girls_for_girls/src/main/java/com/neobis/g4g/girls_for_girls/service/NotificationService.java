package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.NotificationDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Notification;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.repository.NotificationRepo;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepo notificationRepo;
    private final UserRepository userRepository;

    public List<NotificationDTO> getAllNotifications() {
        return NotificationDTO.notificationToNotificationDtoList(notificationRepo.findAll());
    }

    public ResponseEntity<?> getNotificationsByUser(User user) {
        return ResponseEntity.ok(NotificationDTO.notificationToNotificationDtoList(notificationRepo.findAllByUserId(user.getId())));
    }

    public ResponseEntity<?> addNotification(NotificationDTO notificationDTO) {
        try {
            Notification notification = new Notification();
            notification.setUser(userRepository.findById(notificationDTO.getUserId()).get());
            notification.setHeader(notificationDTO.getHeader());
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

    public ResponseEntity<?> makeReadedNotificationById(Long id) {
        return notificationRepo.findById(id)
                .map(notification -> {
                    notification.setReaded(true);
                    notificationRepo.save(notification);
                    return ResponseEntity.ok("Readed");
                }).orElse(new ResponseEntity<>("Notification with this id: " + id + " not found", HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<?> makeReadedAllNotifications(User user) {
        if (notificationRepo.findAllByUserId(user.getId()).isEmpty()) {
            return new ResponseEntity<String>("User with this id: " + user.getId() + " not found", HttpStatus.NOT_FOUND);
        } else {
            notificationRepo.findAllByUserId(user.getId()).forEach(notification -> {
                notification.setReaded(true);
                notificationRepo.save(notification);
            });
            return ResponseEntity.ok("Readed all notifications");
        }
    }
}
