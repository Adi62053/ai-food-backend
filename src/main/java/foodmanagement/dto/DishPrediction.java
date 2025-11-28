package foodmanagement.dto;

public class DishPrediction {
    private String dishName;
    private String storeName;
    private int todaySales;
    private int predictedQuantity;
    private String trend;
    private int avgWaste;
    private String recommendation;

    // Default constructor
    public DishPrediction() {}

    // Parameterized constructor
    public DishPrediction(String dishName, String storeName, int todaySales, 
                         int predictedQuantity, String trend, int avgWaste) {
        this.dishName = dishName;
        this.storeName = storeName;
        this.todaySales = todaySales;
        this.predictedQuantity = predictedQuantity;
        this.trend = trend;
        this.avgWaste = avgWaste;
        this.recommendation = generateRecommendation(trend, predictedQuantity);
    }

    // Getters and Setters
    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public int getTodaySales() { return todaySales; }
    public void setTodaySales(int todaySales) { this.todaySales = todaySales; }

    public int getPredictedQuantity() { return predictedQuantity; }
    public void setPredictedQuantity(int predictedQuantity) { this.predictedQuantity = predictedQuantity; }

    public String getTrend() { return trend; }
    public void setTrend(String trend) { 
        this.trend = trend;
        this.recommendation = generateRecommendation(trend, this.predictedQuantity);
    }

    public int getAvgWaste() { return avgWaste; }
    public void setAvgWaste(int avgWaste) { this.avgWaste = avgWaste; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    // AI Recommendation Logic
    private String generateRecommendation(String trend, int predictedQuantity) {
        switch (trend) {
            case "📈 HOT":
                return "Increase production by " + (predictedQuantity - todaySales) + " units";
            case "📉 SLOW":
                return "Reduce production by " + (todaySales - predictedQuantity) + " units";
            case "🔴 WASTE":
                return "Significant waste detected - reduce production aggressively";
            case "➡️ NORMAL":
                return "Maintain current production levels";
            default:
                return "Monitor sales pattern";
        }
    }

    @Override
    public String toString() {
        return "DishPrediction{" +
                "dishName='" + dishName + '\'' +
                ", storeName='" + storeName + '\'' +
                ", todaySales=" + todaySales +
                ", predictedQuantity=" + predictedQuantity +
                ", trend='" + trend + '\'' +
                ", avgWaste=" + avgWaste +
                ", recommendation='" + recommendation + '\'' +
                '}';
    }
}