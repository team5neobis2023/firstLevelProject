package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.OrderDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Basket;
import com.neobis.g4g.girls_for_girls.data.entity.Order;
import com.neobis.g4g.girls_for_girls.data.entity.Product;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.repository.BasketRepository;
import com.neobis.g4g.girls_for_girls.repository.OrderRepo;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.neobis.g4g.girls_for_girls.data.dto.OrderDTO.orderToOrderDtoList;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserRepository userRepository;
    private final BasketRepository basketRepository;

    @Autowired
    public OrderService(OrderRepo orderRepo, UserRepository userRepository, BasketRepository basketRepository) {
        this.orderRepo = orderRepo;
        this.userRepository = userRepository;
        this.basketRepository = basketRepository;
    }

    public List<OrderDTO> getAllOrders() {
        return orderToOrderDtoList(orderRepo.findAll());
    }

    public List<OrderDTO> getMyOrders(User user){
        return orderToOrderDtoList(orderRepo.findByUser(user));
    }

    public Page<OrderDTO> getMyOrders(Pageable pageable, User user) {
        List<OrderDTO> list = orderToOrderDtoList(orderRepo.findByUser(user));
        return new PageImpl<>(list, pageable, list.size());
    }

    public ResponseEntity<String> addOrder() {
            List<Basket> baskets = basketRepository.findByUser(getCurrentUser());
            if(baskets.size()==0){
                return ResponseEntity.badRequest().body("Basket is empty");
            }
            for (Basket basket: baskets) {
                Order order = new Order();
                order.setSize(basket.getSize());
                order.setUser(getCurrentUser());
                order.setProduct(basket.getProduct());
                order.setAmount(basket.getAmount());
                order.setOrderDate(Timestamp.valueOf(LocalDateTime.now()));
                orderRepo.save(order);
                basketRepository.deleteById(basket.getId());
            }
            return ResponseEntity.ok("Orders were created");
    }

    private User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(((UserDetails)principal).getUsername()).get();
    }

    public ResponseEntity<String> deleteOrder(Long id) {
        if (orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
            return ResponseEntity.ok("Order is deleted");
        }
        else return new ResponseEntity<>("There is no such order", HttpStatus.NOT_FOUND);
    }

}
