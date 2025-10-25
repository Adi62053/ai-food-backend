package foodmanagement.controller;

import foodmanagement.dto.OrderRequest;
import foodmanagement.model.Order;
import foodmanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders") // ✅ CHANGED to match your existing pattern
@CrossOrigin(origins = "http://localhost:5173") // ✅ Match your frontend URL
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            System.out.println("📦 ORDER CONTROLLER CALLED!");
            System.out.println("Customer ID: " + orderRequest.getCustomerId());
            
            Order savedOrder = orderService.createOrder(orderRequest);
            System.out.println("✅ Order saved with ID: " + savedOrder.getOrderId());
            
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
 // Add these endpoints to your existing OrderController.java

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrdersWithItems() {
        try {
            List<Order> orders = orderService.getAllOrdersWithItems();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            System.err.println("❌ Error fetching orders: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        try {
            Map<String, Object> stats = orderService.getDashboardStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("❌ Error fetching dashboard stats: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}