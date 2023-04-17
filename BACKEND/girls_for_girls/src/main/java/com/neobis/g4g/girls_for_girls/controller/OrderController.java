package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.OrderDTO;
import com.neobis.g4g.girls_for_girls.data.dto.ProductDTO;
import com.neobis.g4g.girls_for_girls.service.OrderService;
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
@RequestMapping("/api/v1/order")
@Tag(
        name = "Контроллер для управления записями заказов",
        description = "В этом контроллере вы сможете добавлять, удалять, получать, а также обновлять заказы"
)
public class OrderController {

    private final OrderService orderService;

    @SecurityRequirement(name = "JWT")
    @GetMapping
    @Operation(
            summary = "Получение всех заказов",
            tags = "Заказ"
    )
    private List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение заказа",
            description = "Позволяет получить заказ по его ID",
            tags = "Заказ"
    )
    public ResponseEntity<?> getOrderId(@PathVariable
                                          @Parameter(description = "Идентификатор заказа")
                                          Long id) {
        return orderService.getOrderById(id);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Добавление заказа",
            tags = "Заказ"
    )
    @PostMapping()
    public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.addOrder(orderDTO);
    }

    @Operation(
            summary = "Изменить заказ",
            description = "Позволяет изменить заказ по его ID",
            tags = "Заказ"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable
                                           @Parameter(description = "Идентификатор заказа")
                                           Long id,
                                           @RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(id, orderDTO);
    }

    @Operation(
            summary = "Удалить заказ",
            description = "Позволяет удалить заказ по его ID",
            tags = "Заказ"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable
                                                @Parameter(description = "Идентификатор заказа")
                                                Long id) {
        return orderService.deleteOrder(id);
    }

}
