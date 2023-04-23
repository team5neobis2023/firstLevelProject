package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.OrderDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Product;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@Tag(
        name = "Контроллер для управления записями заказов",
        description = "В этом контроллере вы сможете добавлять, удалять, получать, а также обновлять заказы"
)
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping
    @Operation(
            summary = "Получение всех заказов"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/myOrders/byPages")
    @Operation(
            summary = "Получить заказы авторизованного пользователя с пагинацией"
    )
    public Page<OrderDTO> getMyOrders(@PageableDefault Pageable pageable,
                                      @AuthenticationPrincipal User user) {
        return orderService.getMyOrders(pageable, user);
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/myOrders")
    @Operation(
            summary = "Получение заказов авторизованного пользователя"
    )
    public List<OrderDTO> getMyOrders(@AuthenticationPrincipal User user) {
        return orderService.getMyOrders(user);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Оформление заказа",
            description = "Вся корзина пользователя превращается в заказ"
    )
    @PostMapping()
    public ResponseEntity<String> addOrder() {
        return orderService.addOrder();
    }

    @Operation(
            summary = "Удалить заказ",
            description = "Позволяет удалить заказ по его ID"
    )
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable
                                                @Parameter(description = "Идентификатор заказа")
                                                Long id) {
        return orderService.deleteOrder(id);
    }

}
