package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Notification;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDTO {

    private String message;
    private Long userId;

    public static NotificationDTO notificationToNotificationDto(Notification notification) {
        return NotificationDTO.builder()
                .message(notification.getMessage())
                .userId(notification.getUser().getId())
                .build();
    }

    public static List<NotificationDTO> notificationToNotificationDtoList(List<Notification> notifications) {
        return notifications.stream().map(NotificationDTO::notificationToNotificationDto).collect(Collectors.toList());
    }
}
