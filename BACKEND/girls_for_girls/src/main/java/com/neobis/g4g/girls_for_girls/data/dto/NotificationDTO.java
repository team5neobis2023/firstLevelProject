package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Notification;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private String message;
    private Timestamp recTime;
    private String header;
    private Long userId;

    public static NotificationDTO notificationToNotificationDto(Notification notification) {
        return NotificationDTO.builder()
                .header(notification.getHeader())
                .userId(notification.getUser().getId())
                .build();
    }

    public static NotificationDTO getDetailedNotification(Notification notification) {
        return NotificationDTO.builder()
                .header(notification.getHeader())
                .message(notification.getMessage())
                .recTime(notification.getRecTime())
                .build();
    }

    public static List<NotificationDTO> notificationToNotificationDtoList(List<Notification> notifications) {
        return notifications.stream().map(NotificationDTO::notificationToNotificationDto).collect(Collectors.toList());
    }
}
