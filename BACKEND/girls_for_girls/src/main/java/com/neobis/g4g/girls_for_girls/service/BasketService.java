package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.AddBasketDTO;
import com.neobis.g4g.girls_for_girls.data.dto.DeleteFromBasketDTO;
import com.neobis.g4g.girls_for_girls.data.dto.GetBasketDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Basket;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.repository.BasketRepository;
import com.neobis.g4g.girls_for_girls.repository.ProductRepo;
import com.neobis.g4g.girls_for_girls.repository.SizeRepository;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.GetBasketDTO.toGetBasketDTO;

@Service
public class BasketService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final SizeRepository sizeRepository;
    private final ProductRepo productRepo;

    @Autowired
    public BasketService(BasketRepository basketRepository, UserRepository userRepository, SizeRepository sizeRepository, ProductRepo productRepo) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.sizeRepository = sizeRepository;
        this.productRepo = productRepo;
    }

    public List<GetBasketDTO> getMyBasket(){
        return toGetBasketDTO(basketRepository.findByUser(getCurrentUser()));
    }

    public ResponseEntity<String> addToBasket(AddBasketDTO addBasketDTO,
                                              BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        if (productRepo.existsById(addBasketDTO.getProductId())){
            if(sizeRepository.existsById(addBasketDTO.getSizeId())){
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = userRepository.findByEmail(((UserDetails)principal).getUsername()).get();
                basketRepository.save(
                        Basket.builder()
                                .amount(addBasketDTO.getAmount())
                                .size(sizeRepository.findById(addBasketDTO.getSizeId()).get())
                                .product(productRepo.findById(addBasketDTO.getProductId()).get())
                                .user(user)
                                .build()
                );
                return ResponseEntity.ok("Added to basket");

            }else{
                return ResponseEntity.badRequest().body("Write sizeId correctly");
            }
        }else{
            return ResponseEntity.badRequest().body("Write productId correctly");
        }
    }

    public ResponseEntity<String> updateBasket(AddBasketDTO addBasketDTO,
                                              BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        if (productRepo.existsById(addBasketDTO.getProductId())){
            if(sizeRepository.existsById(addBasketDTO.getSizeId())){
                if(basketRepository.existsByProductIdAndUser(addBasketDTO.getProductId(), getCurrentUser())) {
                    Basket basket = basketRepository.findByProductIdAndUser(addBasketDTO.getProductId(), getCurrentUser()).get();
                    basketRepository.save(
                            Basket.builder()
                                    .id(basket.getId())
                                    .amount(addBasketDTO.getAmount())
                                    .size(sizeRepository.findById(addBasketDTO.getSizeId()).get())
                                    .product(productRepo.findById(addBasketDTO.getProductId()).get())
                                    .user(basket.getUser())
                                    .build()
                    );
                    return ResponseEntity.ok("Basket updated");
                }else{
                    return ResponseEntity.badRequest().body("Doesn't exist in basket");
                }
            }else{
                return ResponseEntity.badRequest().body("Write sizeId correctly");
            }
        }else{
            return ResponseEntity.badRequest().body("Write productId correctly");
        }
    }

    public ResponseEntity<String> deleteFromBasketById(DeleteFromBasketDTO deleteFromBasketDTO){
        if(basketRepository.existsById(deleteFromBasketDTO.getId())){
            basketRepository.deleteById(deleteFromBasketDTO.getId());
            return ResponseEntity.ok("Successfully deleted");
        }else{
            return ResponseEntity.badRequest().body("Basket with id " + deleteFromBasketDTO.getId() + " wasn't found");
        }
    }

    private User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(((UserDetails)principal).getUsername()).get();
    }

    private StringBuilder getErrorMsg(BindingResult bindingResult){
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        }
        return errorMsg;
    }
}
