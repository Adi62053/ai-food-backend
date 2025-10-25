// File: src/main/java/foodmanagement/dto/OrderItemRequest.java
package foodmanagement.dto;

public class OrderItemRequest {
    private Integer dishId;
    private String dishName;
    private String dishType;
    private String dishCategory;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;

    // Constructors
    public OrderItemRequest() {}

    public OrderItemRequest(Integer dishId, String dishName, String dishType, 
                           String dishCategory, Integer quantity, Double unitPrice, Double totalPrice) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishType = dishType;
        this.dishCategory = dishCategory;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public Integer getDishId() { return dishId; }
    public void setDishId(Integer dishId) { this.dishId = dishId; }

    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    public String getDishType() { return dishType; }
    public void setDishType(String dishType) { this.dishType = dishType; }

    public String getDishCategory() { return dishCategory; }
    public void setDishCategory(String dishCategory) { this.dishCategory = dishCategory; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
}