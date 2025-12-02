package UzimaBoreholeSystem.models;

public class PumpInstallation {
    private String pumpType;
    private double cost;
    
    // Constructors
    public PumpInstallation() {}
    
    public PumpInstallation(String pumpType, double cost) {
        this.pumpType = pumpType;
        this.cost = cost;
    }
    
    // Getters and Setters
    public String getPumpType() {
        return pumpType;
    }
    
    public void setPumpType(String pumpType) {
        this.pumpType = pumpType;
    }
    
    public double getCost() {
        return cost;
    }
    
    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public double getAmount() {
        return cost;
    }
    
    public double getPlumbingFee() {
        return cost * 0.2; // 20% of pump cost as plumbing fee
    }
}
