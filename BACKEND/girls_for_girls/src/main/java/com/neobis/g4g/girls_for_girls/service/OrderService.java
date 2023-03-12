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

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final UserRepository userRepo;

    public List<OrderDTO> getAllOrders() {
        return OrderDTO.orderToOrderDtoList(orderRepo.findAll());
    }

    public ResponseEntity<?> getOrderId(Long id) {
        if (orderRepo.findById(id).isPresent()) {
            return ResponseEntity.ok(OrderDTO.orderToOrderDto(orderRepo.findById(id).get()));
        }
        return new ResponseEntity<String>("Order with this id: " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> addOrder(OrderDTO orderDTO) {
        try {
            if (productRepo.findById(orderDTO.getProductId()).isPresent() && userRepo.findById(orderDTO.getUserId()).isPresent()) {
                return ResponseEntity.badRequest().body("The order already exists");
            }
            Order order = new Order();
            Product product = productRepo.findById(orderDTO.getProductId()).get();
            order.setUser(userRepo.findById(orderDTO.getUserId()).get());
            order.setProduct(product);
            order.setAmount(product.getPrice());
            orderRepo.save(order);
            return new ResponseEntity<String>("Product is created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Product isn't created", HttpStatus.BAD_REQUEST);
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
                }).orElse(new ResponseEntity<String>("Order with this id: " + id + " not foudn", HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteOrder(Long id) {
        if (orderRepo.existsById(id)) {
            productRepo.deleteById(id);
            return ResponseEntity.ok("Product is deleted");
        }
        else return new ResponseEntity<String>("There is no such product", HttpStatus.NOT_FOUND);
    }
}
