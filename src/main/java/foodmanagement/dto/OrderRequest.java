// File: src/main/java/foodmanagement/dto/OrderRequest.java
package foodmanagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class OrderRequest {
    private Integer customerId;
    private String storeName;
    private Double totalAmount;
    private String orderStatus;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private List<OrderItemRequest> orderItems;

    // Constructors
    public OrderRequest() {}

    public OrderRequest(Integer customerId, String storeName, Double totalAmount, 
                       String orderStatus, LocalDate orderDate, LocalTime orderTime, 
                       List<OrderItemRequest> orderItems) {
        this.customerId = customerId;
        this.storeName = storeName;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderItems = orderItems;
    }

    // Getters and Setters
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public LocalTime getOrderTime() { return orderTime; }
    public void setOrderTime(LocalTime orderTime) { this.orderTime = orderTime; }

    public List<OrderItemRequest> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemRequest> orderItems) { this.orderItems = orderItems; }
}