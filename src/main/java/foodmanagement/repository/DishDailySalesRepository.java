package foodmanagement.repository;

import foodmanagement.model.DishDailySales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DishDailySalesRepository extends JpaRepository<DishDailySales, Long> {
    
    // Find recent sales data for a specific store
    List<DishDailySales> findByStoreNameOrderBySaleDateDesc(String storeName);
    
    // Find sales data for a specific dish in a store
    List<DishDailySales> findByStoreNameAndDishNameOrderBySaleDateDesc(String storeName, String dishName);
    
    // Find recent sales data (last 30 days) for a store
    List<DishDailySales> findByStoreNameAndSaleDateAfterOrderBySaleDateDesc(
        String storeName, Date sinceDate);
    
    // Find sales for specific dish in store after date
    List<DishDailySales> findByStoreNameAndDishNameAndSaleDateAfterOrderBySaleDateDesc(
        String storeName, String dishName, Date sinceDate);
    
    // Find top N recent records for a store
    List<DishDailySales> findTop30ByStoreNameOrderBySaleDateDesc(String storeName);
    
    // Custom query to get dish sales summary for predictions
    @Query("SELECT d FROM DishDailySales d WHERE d.storeName = :storeName AND d.saleDate >= :startDate ORDER BY d.saleDate DESC")
    List<DishDailySales> findRecentSalesForPredictions(
        @Param("storeName") String storeName, 
        @Param("startDate") Date startDate);
    
    // Check if data exists for a store
    boolean existsByStoreName(String storeName);
    
    // Find by store name and sale date range - ADDED FOR TREND ANALYSIS
    @Query("SELECT d FROM DishDailySales d WHERE d.storeName = :storeName AND d.dishName = :dishName AND d.saleDate >= :startDate ORDER BY d.saleDate DESC")
    List<DishDailySales> findDishSalesByDateRange(
        @Param("storeName") String storeName,
        @Param("dishName") String dishName,
        @Param("startDate") Date startDate);
}