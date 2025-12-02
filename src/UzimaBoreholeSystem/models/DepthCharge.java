package UzimaBoreholeSystem.models;

/**
 * Depth charge model for calculating depth-based costs
 */
public class DepthCharge {
    private int rangeFrom;
    private int rangeTo;
    private double costPerMeter;
    
    // Constructors
    public DepthCharge() {}
    
    public DepthCharge(int rangeFrom, int rangeTo, double costPerMeter) {
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.costPerMeter = costPerMeter;
    }
    
    // Getters and Setters
    public int getRangeFrom() {
        return rangeFrom;
    }
    
    public void setRangeFrom(int rangeFrom) {
        this.rangeFrom = rangeFrom;
    }
    
    public int getRangeTo() {
        return rangeTo;
    }
    
    public void setRangeTo(int rangeTo) {
        this.rangeTo = rangeTo;
    }
    
    public double getCostPerMeter() {
        return costPerMeter;
    }
    
    public void setCostPerMeter(double costPerMeter) {
        this.costPerMeter = costPerMeter;
    }
    
    public double getAmount() {
        return costPerMeter;
    }
}
