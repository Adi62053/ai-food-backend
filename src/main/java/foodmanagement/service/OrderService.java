// File: src/main/java/foodmanagement/service/OrderService.java
package foodmanagement.service;

import foodmanagement.dto.OrderRequest;
import foodmanagement.dto.OrderItemRequest;
import foodmanagement.model.Order;
import foodmanagement.model.OrderItem;
import foodmanagement.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        // Create main order
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setStoreName(orderRequest.getStoreName());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setOrderStatus(orderRequest.getOrderStatus());
        order.setOrderDate(orderRequest.getOrderDate());
        order.setOrderTime(orderRequest.getOrderTime());

        // Create order items
        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(this::convertToOrderItem)
                .collect(Collectors.toList());

        // Set bidirectional relationship
        orderItems.forEach(item -> item.setOrder(order));
        order.setOrderItems(orderItems);

        // Save order (cascade will save order items)
        return orderRepository.save(order);
    }

    private OrderItem convertToOrderItem(OrderItemRequest itemRequest) {
        OrderItem orderItem = new OrderItem();
        orderItem.setDishId(itemRequest.getDishId());
        orderItem.setDishName(itemRequest.getDishName());
        orderItem.setDishType(itemRequest.getDishType());
        orderItem.setDishCategory(itemRequest.getDishCategory());
        orderItem.setQuantity(itemRequest.getQuantity());
        orderItem.setUnitPrice(itemRequest.getUnitPrice());
        orderItem.setTotalPrice(itemRequest.getTotalPrice());
        return orderItem;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }
 // Add these methods to your existing OrderService.java
    public List<Order> getAllOrdersWithItems() {
        return orderRepository.findAll();
    }

    public Map<String, Object> getDashboardStats() {
        List<Order> allOrders = orderRepository.findAll();
        
        // Calculate statistics
        long totalOrders = allOrders.size();
        double totalRevenue = allOrders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();
        long totalCustomers = allOrders.stream()
                .map(Order::getCustomerId)
                .distinct()
                .count();
        
        // Calculate waste reduced (mock calculation - adjust based on your logic)
        double wasteReduced = allOrders.size() * 0.3; // 0.3kg per order
        
        // Get popular dishes
        Map<String, Long> dishCount = new HashMap<>();
        for (Order order : allOrders) {
            for (OrderItem item : order.getOrderItems()) {
                dishCount.merge(item.getDishName(), 1L, Long::sum);
            }
        }
        
        List<Map<String, Object>> popularDishes = dishCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(entry -> {
                    Map<String, Object> dish = new HashMap<>();
                    dish.put("name", entry.getKey());
                    dish.put("orders", entry.getValue());
                    return dish;
                })
                .collect(Collectors.toList());
        
        // Create stats object
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOrders", totalOrders);
        stats.put("totalRevenue", totalRevenue);
        stats.put("totalCustomers", totalCustomers);
        stats.put("wasteReduced", Math.round(wasteReduced * 100.0) / 100.0);
        stats.put("popularDishes", popularDishes);
        
        return stats;
    }
}