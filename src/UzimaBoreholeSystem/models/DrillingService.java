package UzimaBoreholeSystem.models;

/**
 * Drilling service model with down payment information
 */
public class DrillingService {
    private String drillingType;
    private double downPayment;
    
    // Constructors
    public DrillingService() {}
    
    public DrillingService(String drillingType, double downPayment) {
        this.drillingType = drillingType;
        this.downPayment = downPayment;
    }
    
    // Getters and Setters
    public String getDrillingType() {
        return drillingType;
    }
    
    public void setDrillingType(String drillingType) {
        this.drillingType = drillingType;
    }
    
    public double getDownPayment() {
        return downPayment;
    }
    
    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }
    
    public double getAmount() {
        return downPayment;
    }
}
