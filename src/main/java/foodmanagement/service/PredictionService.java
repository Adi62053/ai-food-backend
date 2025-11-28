package foodmanagement.service;

import foodmanagement.dto.DishPrediction;
import foodmanagement.dto.SalesTrend;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class PredictionService {

    // Enhanced sample data with proper dish lists
    private final Map<String, List<String>> storeDishes = createStoreDishes();

    private Map<String, List<String>> createStoreDishes() {
        Map<String, List<String>> dishes = new HashMap<>();
        
        dishes.put("Hyderabad", Arrays.asList(
            "Chicken Biryani", "Butter Naan", "Veg Fried Rice", 
            "Fish Curry", "Veg Noodles", "Chicken Curry"
        ));
        
        dishes.put("Vijayawada", Arrays.asList(
            "Chicken Biryani", "Butter Naan", "Veg Fried Rice", 
            "Mutton Curry", "Paneer Butter Masala", "Egg Fried Rice"
        ));
        
        dishes.put("Guntur", Arrays.asList(
            "Chicken Biryani", "Butter Naan", "Veg Fried Rice", 
            "Chicken Curry", "Egg Fried Rice", "Fish Fry"
        ));
        
        return dishes;
    }

    public List<String> getAvailableStores() {
        System.out.println("🏪 Returning available stores: " + storeDishes.keySet());
        return new ArrayList<>(storeDishes.keySet());
    }

    public List<DishPrediction> getStorePredictions(String storeName) {
        System.out.println("🔮 Generating predictions for store: " + storeName);
        
        // Check if store exists
        if (!storeDishes.containsKey(storeName)) {
            System.out.println("❌ Store not found: " + storeName);
            return new ArrayList<>();
        }
        
        List<DishPrediction> predictions = new ArrayList<>();
        List<String> dishes = storeDishes.get(storeName);
        
        System.out.println("🔮 Dishes for " + storeName + ": " + dishes);
        
        Random random = new Random();
        
        for (String dish : dishes) {
            int todaySales = 20 + random.nextInt(30); // 20-50 sales
            int predicted = calculatePredictedQuantity(dish, todaySales, random);
            
            String trend = determineTrend(dish, todaySales, predicted, random);
            int avgWaste = calculateAvgWaste(dish, trend, random);
            
            // ✅ FIXED: Using correct DishPrediction constructor with 6 parameters
            DishPrediction prediction = new DishPrediction(
                dish,           // dishName
                storeName,      // storeName  
                todaySales,     // todaySales
                predicted,      // predictedQuantity
                trend,          // trend
                avgWaste        // avgWaste
            );
            
            predictions.add(prediction);
            System.out.println("🍽️  " + dish + ": " + todaySales + " → " + predicted + " (" + trend + ") Waste: " + avgWaste);
        }
        
        System.out.println("✅ Generated " + predictions.size() + " predictions for " + storeName);
        return predictions;
    }

    private int calculatePredictedQuantity(String dish, int todaySales, Random random) {
        // Different prediction logic based on dish type
        if (dish.contains("Biryani")) {
            return todaySales + random.nextInt(15); // Biryani usually sells more
        } else if (dish.contains("Fish") || dish.contains("Mutton")) {
            return todaySales - random.nextInt(8); // Premium items might sell less
        } else {
            return todaySales + random.nextInt(10) - 5; // Normal variation
        }
    }

    private String determineTrend(String dish, int todaySales, int predicted, Random random) {
        int difference = predicted - todaySales;
        
        if (difference > 8) {
            return "📈 HOT";
        } else if (difference < -8) {
            // Fish and Mutton have higher waste probability
            if ((dish.contains("Fish") || dish.contains("Mutton")) && random.nextDouble() > 0.6) {
                return "🔴 WASTE";
            }
            return "📉 SLOW";
        } else {
            return "➡️ NORMAL";
        }
    }

    private int calculateAvgWaste(String dish, String trend, Random random) {
        // Calculate average waste based on dish type and trend
        int baseWaste;
        
        switch (trend) {
            case "🔴 WASTE":
                baseWaste = 8 + random.nextInt(7); // 8-15 waste units
                break;
            case "📉 SLOW":
                baseWaste = 4 + random.nextInt(5); // 4-9 waste units
                break;
            case "📈 HOT":
                baseWaste = 1 + random.nextInt(3); // 1-4 waste units
                break;
            default: // NORMAL
                baseWaste = 2 + random.nextInt(4); // 2-6 waste units
        }
        
        // Fish and Mutton dishes tend to have more waste
        if (dish.contains("Fish") || dish.contains("Mutton")) {
            baseWaste += 3;
        }
        
        return baseWaste;
    }

    public List<SalesTrend> getDishTrend(String dishName, String storeName) {
        System.out.println("📊 Generating trend for " + dishName + " in " + storeName);
        
        List<SalesTrend> trend = new ArrayList<>();
        LocalDate today = LocalDate.now();
        Random random = new Random(dishName.hashCode() + storeName.hashCode()); // Consistent randomness
        
        // Generate 7 days of historical data with realistic patterns
        int baseQuantity = 20 + random.nextInt(20); // Base 20-40 sales
        
        for (int i = 7; i > 0; i--) {
            LocalDate date = today.minusDays(i);
            
            // Get day of week name
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            
            // Weekend effect
            boolean isWeekend = date.getDayOfWeek().getValue() >= 6; // Saturday(6) or Sunday(7)
            int weekendBoost = isWeekend ? 10 : 0;
            
            // Dish-specific patterns
            int dishVariation = 0;
            if (dishName.contains("Biryani")) dishVariation = 5;
            if (dishName.contains("Fish") || dishName.contains("Mutton")) dishVariation = -3;
            
            int quantity = baseQuantity + random.nextInt(15) + weekendBoost + dishVariation;
            
            // Calculate waste quantity (5-20% of sales)
            int wastedQuantity = (int)(quantity * (0.05 + random.nextDouble() * 0.15));
            
            // ✅ UPDATED: Use your SalesTrend constructor signature: (date, quantitySold, wastedQuantity, dayOfWeek)
            SalesTrend salesTrend = new SalesTrend(
                date.toString(), 
                quantity, 
                wastedQuantity, 
                dayOfWeek
            );
            
            trend.add(salesTrend);
        }
        
        System.out.println("✅ Generated " + trend.size() + " days of trend data for " + dishName);
        return trend;
    }

    // ✅ ADD THIS METHOD for the /api/predictions/health endpoint
    public String healthCheck() {
        return "Prediction Service is running! " + LocalDate.now() + 
               " | Stores: " + String.join(", ", getAvailableStores());
    }

    // ✅ ADD THIS METHOD for the /api/predictions/stores endpoint  
    public List<DishPrediction> getAllStorePredictions() {
        System.out.println("🔮 Generating predictions for ALL stores");
        
        List<DishPrediction> allPredictions = new ArrayList<>();
        List<String> stores = getAvailableStores();
        
        for (String store : stores) {
            List<DishPrediction> storePredictions = getStorePredictions(store);
            allPredictions.addAll(storePredictions);
        }
        
        System.out.println("✅ Generated total " + allPredictions.size() + " predictions across " + stores.size() + " stores");
        return allPredictions;
    }
}