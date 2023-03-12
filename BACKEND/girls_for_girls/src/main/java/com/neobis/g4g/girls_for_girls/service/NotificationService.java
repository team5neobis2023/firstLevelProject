package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.NotificationDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Notification;
import com.neobis.g4g.girls_for_girls.repository.NotificationRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepo notificationRepo;

    public List<NotificationDTO> getAllNotifications() {
        return NotificationDTO.notificationToNotificationDtoList(notificationRepo.findAll());
    }

    public ResponseEntity<?> getNotificationId(Long id) {
        if (notificationRepo.findById(id).isPresent()) {
            return ResponseEntity.ok(NotificationDTO.notificationToNorificationDto(notificationRepo.findById(id).get()));
        }
        return new ResponseEntity<String>("Notification with this id: " + id + " not found", HttpStatus.NOT_FOUND);
    }
}
