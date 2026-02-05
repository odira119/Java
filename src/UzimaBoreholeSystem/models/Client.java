package UzimaBoreholeSystem.models;

import java.sql.Timestamp;

public class Client {
    private String clientId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String password;
    private String clientCategory;
    private String boreholeLocation;
    private int depthOrHeight;
    private String pumpType;
    private String pipeType;
    private int pipeDiameter;
    private int pipeLength;
    private int numberOfOutlets;
    private String drillingType;
    private double surveyFee;
    private double localAuthorityFee;
    private double drillingFee;
    private double pumpInstallationFee;
    private double plumbingFee;
    private double depthCharge;
    private double subtotal;
    private double taxPaid;
    private double totalCost;
    private String paymentStatus;
    private Timestamp createdAt;
    
    // Constructors
    public Client() {
        this.paymentStatus = "Pending";
    }
    
    public Client(String clientId, String name, String address, String phone, String email) {
        this.clientId = clientId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.paymentStatus = "Pending";
    }
    
    // Getters and Setters
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getClientCategory() {
        return clientCategory;
    }
    
    public void setClientCategory(String clientCategory) {
        this.clientCategory = clientCategory;
    }
    
    public String getBoreholeLocation() {
        return boreholeLocation;
    }
    
    public void setBoreholeLocation(String boreholeLocation) {
        this.boreholeLocation = boreholeLocation;
    }
    
    public int getDepthOrHeight() {
        return depthOrHeight;
    }
    
    public void setDepthOrHeight(int depthOrHeight) {
        this.depthOrHeight = depthOrHeight;
    }
    
    public String getPumpType() {
        return pumpType;
    }
    
    public void setPumpType(String pumpType) {
        this.pumpType = pumpType;
    }
    
    public String getPipeType() {
        return pipeType;
    }
    
    public void setPipeType(String pipeType) {
        this.pipeType = pipeType;
    }
    
    public int getPipeDiameter() {
        return pipeDiameter;
    }
    
    public void setPipeDiameter(int pipeDiameter) {
        this.pipeDiameter = pipeDiameter;
    }
    
    public int getPipeLength() {
        return pipeLength;
    }
    
    public void setPipeLength(int pipeLength) {
        this.pipeLength = pipeLength;
    }
    
    public int getNumberOfOutlets() {
        return numberOfOutlets;
    }
    
    public void setNumberOfOutlets(int numberOfOutlets) {
        this.numberOfOutlets = numberOfOutlets;
    }
    
    public String getDrillingType() {
        return drillingType;
    }
    
    public void setDrillingType(String drillingType) {
        this.drillingType = drillingType;
    }
    
    public double getSurveyFee() {
        return surveyFee;
    }
    
    public void setSurveyFee(double surveyFee) {
        this.surveyFee = surveyFee;
    }
    
    public double getLocalAuthorityFee() {
        return localAuthorityFee;
    }
    
    public void setLocalAuthorityFee(double localAuthorityFee) {
        this.localAuthorityFee = localAuthorityFee;
    }
    
    public double getDrillingFee() {
        return drillingFee;
    }
    
    public void setDrillingFee(double drillingFee) {
        this.drillingFee = drillingFee;
    }
    
    public double getPumpInstallationFee() {
        return pumpInstallationFee;
    }
    
    public void setPumpInstallationFee(double pumpInstallationFee) {
        this.pumpInstallationFee = pumpInstallationFee;
    }
    
    public double getPlumbingFee() {
        return plumbingFee;
    }
    
    public void setPlumbingFee(double plumbingFee) {
        this.plumbingFee = plumbingFee;
    }
    
    public double getDepthCharge() {
        return depthCharge;
    }
    
    public void setDepthCharge(double depthCharge) {
        this.depthCharge = depthCharge;
    }
    
    public double getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    public double getTaxPaid() {
        return taxPaid;
    }
    
    public void setTaxPaid(double taxPaid) {
        this.taxPaid = taxPaid;
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    // Convenience methods for UI compatibility
    public String getPhoneNumber() {
        return phone;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phone = phoneNumber;
    }
    
    public String getLocation() {
        return boreholeLocation;
    }
    
    public void setLocation(String location) {
        this.boreholeLocation = location;
    }
    
    public String getCategory() {
        return clientCategory;
    }
    
    public void setCategory(String category) {
        this.clientCategory = category;
    }
    
    public int getDepth() {
        return depthOrHeight;
    }
    
    public void setDepth(int depth) {
        this.depthOrHeight = depth;
    }
    
    public String getPlumbingSpecs() {
        return pipeType;
    }
    
    public void setPlumbingSpecs(String plumbingSpecs) {
        this.pipeType = plumbingSpecs;
    }
    
    public String getStatus() {
        return paymentStatus;
    }
    
    public void setStatus(String status) {
        this.paymentStatus = status;
    }
}
