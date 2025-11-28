package foodmanagement.controller;

import foodmanagement.dto.DishPrediction;
import foodmanagement.dto.SalesTrend;
import foodmanagement.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/predictions") // CHANGED: Remove "/api" from here
@CrossOrigin(origins = "http://localhost:3000")
public class PredictionController {
    
    @Autowired
    private PredictionService predictionService;
    
    // 🎯 COMPREHENSIVE DEBUG
    @PostConstruct
    public void init() {
        System.out.println("🎯🎯🎯 PREDICTION CONTROLLER INITIALIZED! 🎯🎯🎯");
        System.out.println("🎯 Package: " + this.getClass().getPackage().getName());
        System.out.println("🎯 Class: " + this.getClass().getSimpleName());
        System.out.println("🎯 Service injected: " + (predictionService != null ? "✅ YES" : "❌ NO"));
        System.out.println("🎯 Base URL: http://localhost:8080/api/predictions");
        System.out.println("🎯 Available endpoints:");
        System.out.println("🎯   GET  /api/predictions/debug");
        System.out.println("🎯   GET  /api/predictions/health");
        System.out.println("🎯   GET  /api/predictions/stores");
        System.out.println("🎯   GET  /api/predictions/store-names");
        System.out.println("🎯   GET  /api/predictions/test");
    }
    
    /**
     * DEBUG ENDPOINT - Test if controller is working
     * URL: GET http://localhost:8080/api/predictions/debug
     */
    @GetMapping("/debug")
    public ResponseEntity<String> debug() {
        System.out.println("🔧 DEBUG ENDPOINT CALLED - Controller is working!");
        String debugInfo = """
            🔧 PREDICTION CONTROLLER DEBUG INFO:
            ==================================
            ✅ Controller: %s
            ✅ Service: %s
            ✅ Time: %s
            ✅ Status: WORKING PERFECTLY!
            ==================================
            """.formatted(
                this.getClass().getName(),
                predictionService != null ? "INJECTED" : "NULL",
                java.time.LocalDateTime.now()
            );
        return ResponseEntity.ok(debugInfo);
    }
    
    /**
     * SIMPLE TEST ENDPOINT
     * URL: GET http://localhost:8080/api/predictions/test
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("🧪 TEST ENDPOINT CALLED!");
        return ResponseEntity.ok("🧪 Prediction Controller Test - SUCCESS! Timestamp: " + System.currentTimeMillis());
    }
    
    /**
     * Health check endpoint
     * URL: GET http://localhost:8080/api/predictions/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        System.out.println("✅ HEALTH CHECK ENDPOINT CALLED!");
        try {
            String health = predictionService.healthCheck();
            return ResponseEntity.ok("🔮 " + health);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Health check failed: " + e.getMessage());
        }
    }
    
    /**
     * Get AI predictions for ALL dishes across ALL stores
     * URL: GET http://localhost:8080/api/predictions/stores
     */
    @GetMapping("/stores")
    public ResponseEntity<List<DishPrediction>> getAllStoresPredictions() {
        try {
            System.out.println("🔮 GET /api/predictions/stores endpoint called");
            
            List<DishPrediction> allPredictions = new ArrayList<>();
            List<String> stores = predictionService.getAvailableStores();
            
            for (String store : stores) {
                List<DishPrediction> storePredictions = predictionService.getStorePredictions(store);
                allPredictions.addAll(storePredictions);
            }
            
            System.out.println("✅ Generated " + allPredictions.size() + " predictions across " + stores.size() + " stores");
            return ResponseEntity.ok(allPredictions);
            
        } catch (Exception e) {
            System.err.println("❌ Error in /stores: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get list of available store names
     * URL: GET http://localhost:8080/api/predictions/store-names
     */
    @GetMapping("/store-names")
    public ResponseEntity<List<String>> getAvailableStores() {
        System.out.println("🏪 GET /api/predictions/store-names endpoint called");
        try {
            List<String> stores = predictionService.getAvailableStores();
            System.out.println("🏪 Returning stores: " + stores);
            return ResponseEntity.ok(stores);
        } catch (Exception e) {
            System.err.println("❌ Error in store-names: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get predictions for specific store
     * URL: GET http://localhost:8080/api/predictions/{storeName}
     */
    @GetMapping("/{storeName}")
    public ResponseEntity<List<DishPrediction>> getStorePredictions(@PathVariable String storeName) {
        try {
            System.out.println("🔮 GET /api/predictions/" + storeName + " endpoint called");
            List<DishPrediction> predictions = predictionService.getStorePredictions(storeName);
            return ResponseEntity.ok(predictions);
        } catch (Exception e) {
            System.err.println("❌ Error for store " + storeName + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}