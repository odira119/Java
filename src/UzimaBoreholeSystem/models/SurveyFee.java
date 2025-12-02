package UzimaBoreholeSystem.models;

public class SurveyFee {
    private String category;
    private double surveyFee;
    private double localAuthorityFee;
    
    // Constructors
    public SurveyFee() {}
    
    public SurveyFee(String category, double surveyFee, double localAuthorityFee) {
        this.category = category;
        this.surveyFee = surveyFee;
        this.localAuthorityFee = localAuthorityFee;
    }
    
    // Getters and Setters
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
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
    
    public double getAmount() {
        return surveyFee;
    }
}
