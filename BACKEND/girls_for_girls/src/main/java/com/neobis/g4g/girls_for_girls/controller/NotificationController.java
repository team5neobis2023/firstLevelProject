package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.NotificationDTO;
import com.neobis.g4g.girls_for_girls.data.dto.ProductDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Notification;
import com.neobis.g4g.girls_for_girls.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notification")
@Tag(
        name = "Контроллер для управления записями уведомлений",
        description = "В этом контроллере вы сможете добавлять, удалять, получать, а также обновлять уведомления"
)
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @Operation(
            summary = "Получить все уведомления",
            tags = "Уведомление"
    )
    private List<NotificationDTO> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить уведомление",
            description = "Позволяет получить уведомление по его ID",
            tags = "Уведомление"
    )
    public ResponseEntity<?> getNotificationId(@PathVariable
                                          @Parameter(description = "Идентификатор уведомления")
                                          Long id) {
        return notificationService.getNotificationId(id);
    }

}
