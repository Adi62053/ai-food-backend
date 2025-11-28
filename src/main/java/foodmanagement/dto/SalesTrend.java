package foodmanagement.dto;

import java.util.Date;

public class SalesTrend {
    private String date;
    private int quantitySold;
    private int wastedQuantity;
    private String dayOfWeek;
    private boolean isPeakDay;

    // Default constructor
    public SalesTrend() {}

    // Parameterized constructor
    public SalesTrend(String date, int quantitySold, int wastedQuantity, String dayOfWeek) {
        this.date = date;
        this.quantitySold = quantitySold;
        this.wastedQuantity = wastedQuantity;
        this.dayOfWeek = dayOfWeek;
        this.isPeakDay = dayOfWeek.equals("Saturday") || dayOfWeek.equals("Sunday");
    }

    // Getters and Setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getQuantitySold() { return quantitySold; }
    public void setQuantitySold(int quantitySold) { this.quantitySold = quantitySold; }

    public int getWastedQuantity() { return wastedQuantity; }
    public void setWastedQuantity(int wastedQuantity) { this.wastedQuantity = wastedQuantity; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { 
        this.dayOfWeek = dayOfWeek;
        this.isPeakDay = dayOfWeek.equals("Saturday") || dayOfWeek.equals("Sunday");
    }

    public boolean isPeakDay() { return isPeakDay; }
    public void setPeakDay(boolean peakDay) { isPeakDay = peakDay; }

    // Calculate waste percentage
    public double getWastePercentage() {
        if (quantitySold == 0) return 0.0;
        return (wastedQuantity * 100.0) / quantitySold;
    }

    @Override
    public String toString() {
        return "SalesTrend{" +
                "date='" + date + '\'' +
                ", quantitySold=" + quantitySold +
                ", wastedQuantity=" + wastedQuantity +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", isPeakDay=" + isPeakDay +
                ", wastePercentage=" + String.format("%.1f", getWastePercentage()) + "%" +
                '}';
    }
}