package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.OrderDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Order;
import com.neobis.g4g.girls_for_girls.data.entity.Product;
import com.neobis.g4g.girls_for_girls.repository.OrderRepo;
import com.neobis.g4g.girls_for_girls.repository.ProductRepo;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.OrderDTO.orderToOrderDto;
import static com.neobis.g4g.girls_for_girls.data.dto.OrderDTO.orderToOrderDtoList;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final UserRepository userRepo;

    public List<OrderDTO> getAllOrders() {
        return orderToOrderDtoList(orderRepo.findAll());
    }

    public ResponseEntity<?> getOrderById(Long id) {
        if (orderRepo.findById(id).isPresent()) {
            return ResponseEntity.ok(orderToOrderDto(orderRepo.findById(id).get()));
        }
        return new ResponseEntity<>("Order with this id: " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> addOrder(OrderDTO orderDTO) {
        try {
            if (orderRepo.findByProductIdAndUserId(orderDTO.getProductId(), orderDTO.getUserId()).isPresent()) {
                return ResponseEntity.badRequest().body("The order already exists");
            }
            Order order = new Order();
            Product product = productRepo.findById(orderDTO.getProductId()).get();
            order.setUser(userRepo.findById(orderDTO.getUserId()).get());
            order.setProduct(product);
            order.setOrderDate(Timestamp.valueOf(LocalDateTime.now()));
            order.setAmount(product.getPrice());
            orderRepo.save(order);
            return ResponseEntity.ok("Order was created");
        } catch (Exception e) {
            return new ResponseEntity<>("Order wasn't created", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateOrder(Long id, OrderDTO orderDTO) {
        return orderRepo.findById(id)
                .map(order -> {
                    Product product = productRepo.findById(orderDTO.getProductId()).get();
                    order.setUser(userRepo.findById(orderDTO.getUserId()).get());
                    order.setProduct(product);
                    order.setAmount(product.getPrice());
                    orderRepo.save(order);
                    return ResponseEntity.ok("Order with this id: " + id + " updated");
                }).orElse(new ResponseEntity<>("Order with this id: " + id + " not found", HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteOrder(Long id) {
        if (orderRepo.existsById(id)) {
            productRepo.deleteById(id);
            return ResponseEntity.ok("Order is deleted");
        }
        else return new ResponseEntity<>("There is no such order", HttpStatus.NOT_FOUND);
    }
}
