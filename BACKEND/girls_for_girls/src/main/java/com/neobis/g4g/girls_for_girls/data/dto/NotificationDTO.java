package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Notification;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class NotificationDTO {

    private String message;
    private User user;

    public static NotificationDTO notificationToNorificationDto(Notification notification) {
        return NotificationDTO.builder()
                .message(notification.getMessage())
                .user(notification.getUserId())
                .build();
    }

    public static List<NotificationDTO> notificationToNotificationDtoList(List<Notification> notifications) {
        return notifications.stream().map(NotificationDTO::notificationToNorificationDto).collect(Collectors.toList());
    }
}
