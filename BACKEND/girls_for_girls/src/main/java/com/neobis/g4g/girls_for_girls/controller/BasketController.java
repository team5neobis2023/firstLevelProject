package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.AddBasketDTO;
import com.neobis.g4g.girls_for_girls.data.dto.DeleteFromBasketDTO;
import com.neobis.g4g.girls_for_girls.data.dto.GetBasketDTO;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/myBasket")
@Tag(
        name = "Контроллер для работы с корзиной",
        description = "В этом контроллере вы можете добавлять, удалять, получать данные корзины"
)
public class BasketController {
    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение корзины авторизованного пользователя")
    public List<GetBasketDTO> getMyBasket(){
        return basketService.getMyBasket();
    }

    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Добавление в корзину")
    public ResponseEntity<String> addToBasket(@RequestBody @Valid AddBasketDTO addBasketDTO,
                                        BindingResult bindingResult){
        return basketService.addToBasket(addBasketDTO, bindingResult);
    }

    @PutMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление корзины")
    public ResponseEntity<String> updateBasket(@RequestBody @Valid AddBasketDTO addBasketDTO,
                                         BindingResult bindingResult){
        return basketService.updateBasket(addBasketDTO, bindingResult);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление из корзины по айди")
    public ResponseEntity<String> deleteFromBasket(@RequestBody DeleteFromBasketDTO deleteFromBasketDTO){
        return basketService.deleteFromBasketById(deleteFromBasketDTO);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotAddedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotUpdatedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
