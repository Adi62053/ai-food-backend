package foodmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Entity
@Table(name = "dish_daily_sales")
public class DishDailySales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "sale_date")
    @Temporal(TemporalType.DATE)
    private Date saleDate;

    @Column(name = "quantity_sold")
    private Integer quantitySold;

    @Column(name = "wasted_quantity")
    private Integer wastedQuantity;

    @Column(name = "predicted_demand")
    private Integer predictedDemand;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Default constructor
    public DishDailySales() {}

    // Parameterized constructor
    public DishDailySales(String storeName, String dishName, Date saleDate, 
                         Integer quantitySold, Integer wastedQuantity, 
                         Integer predictedDemand) {
        this.storeName = storeName;
        this.dishName = dishName;
        this.saleDate = saleDate;
        this.quantitySold = quantitySold;
        this.wastedQuantity = wastedQuantity;
        this.predictedDemand = predictedDemand;
        this.createdAt = new Date();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    public Date getSaleDate() { return saleDate; }
    public void setSaleDate(Date saleDate) { this.saleDate = saleDate; }

    public Integer getQuantitySold() { return quantitySold; }
    public void setQuantitySold(Integer quantitySold) { this.quantitySold = quantitySold; }

    public Integer getWastedQuantity() { return wastedQuantity; }
    public void setWastedQuantity(Integer wastedQuantity) { this.wastedQuantity = wastedQuantity; }

    public Integer getPredictedDemand() { return predictedDemand; }
    public void setPredictedDemand(Integer predictedDemand) { this.predictedDemand = predictedDemand; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "DishDailySales{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", dishName='" + dishName + '\'' +
                ", saleDate=" + saleDate +
                ", quantitySold=" + quantitySold +
                ", wastedQuantity=" + wastedQuantity +
                ", predictedDemand=" + predictedDemand +
                ", createdAt=" + createdAt +
                '}';
    }
}