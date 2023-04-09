package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.NotificationDTO;
import com.neobis.g4g.girls_for_girls.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @SecurityRequirement(name = "JWT")
    @GetMapping
    @Operation(
            summary = "Получить все уведомления",
            tags = "Уведомление"
    )
    private List<NotificationDTO> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить уведомление",
            description = "Позволяет получить уведомление по ID пользователя",
            tags = "Уведомление"
    )
    public ResponseEntity<?> getNotificationsByUserID(@PathVariable
                                          @Parameter(description = "Идентификатор пользователя")
                                          Long id) {
        return notificationService.getNotificationsByUserID(id);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping
    @Operation(
            summary = "Добавить уведомление",
            description = "Позволяет добавить уведомление",
            tags = "Уведомление"
    )
    public ResponseEntity<?> addNotification(@RequestBody NotificationDTO notificationDTO) {
        return notificationService.addNotification(notificationDTO);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    @Operation(
            summary = "Изменить уведомление",
            description = "Позволяет изменять уведомление",
            tags = "Уведомление"
    )
    public ResponseEntity<?> updateNotification(@PathVariable
                                                    @Parameter(description = "Идентификатор уведомления")
                                                    Long id,
                                                    @RequestBody NotificationDTO notificationDTO) {
        return notificationService.updateNotification(id, notificationDTO);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить уведомление",
            description = "Позволяет удалять уведомление",
            tags = "Уведомление"
    )
    public ResponseEntity<String> deleteNotification(@PathVariable
                                                         @Parameter(description = "Идентификатор уведомления")
                                                         Long id) {
        return notificationService.deleteNotification(id);
    }

}
