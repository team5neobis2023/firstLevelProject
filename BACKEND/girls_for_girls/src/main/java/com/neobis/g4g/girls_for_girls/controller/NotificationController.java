package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.NotificationDTO;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
@Tag(
        name = "Контроллер для управления записями уведомлений",
        description = "В этом контроллере вы сможете добавлять, удалять, получать, а также обновлять уведомления"
)
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping
    @Operation(
            summary = "Получить все уведомления"
    )
    private List<NotificationDTO> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить уведомление",
            description = "Позволяет получить уведомление по ID пользователя"
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
            description = "Позволяет добавить уведомление"
    )
    public ResponseEntity<?> addNotification(@RequestBody NotificationDTO notificationDTO) {
        return notificationService.addNotification(notificationDTO);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    @Operation(
            summary = "Изменить уведомление",
            description = "Позволяет изменять уведомление"
    )
    public ResponseEntity<?> updateNotification(@PathVariable
                                                    @Parameter(description = "Идентификатор уведомления")
                                                    Long id,
                                                    @RequestBody NotificationDTO notificationDTO) {
        return notificationService.updateNotificationById(id, notificationDTO);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить уведомление",
            description = "Позволяет удалять уведомление"
    )
    public ResponseEntity<String> deleteNotification(@PathVariable
                                                         @Parameter(description = "Идентификатор уведомления")
                                                         Long id) {
        return notificationService.deleteNotificationById(id);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping("/readed-notification{id}")
    @Operation(
            summary = "Прочитать уведомление"
    )
    public ResponseEntity<?> makeReadedNotificationById(@PathVariable
                                                     @Parameter(description = "Идентификатор уведомления")
                                                     Long id) {
        return notificationService.makeReadedNotificationById(id);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping("/readed-all-notifications")
    @Operation(
            summary = "Прочитать все уведомления"
    )
    public ResponseEntity<?> makeReadedAllNotifications(@AuthenticationPrincipal User user) {
        return notificationService.makeReadedAllNotifications(user);
    }
}
