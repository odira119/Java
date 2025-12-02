package UzimaBoreholeSystem.models;

public class Staff {
    private int staffId;
    private String username;
    private String password;
    
    // Constructors
    public Staff() {}
    
    public Staff(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public Staff(int staffId, String username, String password) {
        this.staffId = staffId;
        this.username = username;
        this.password = password;
    }
    
    // Getters and Setters
    public int getStaffId() {
        return staffId;
    }
    
    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return username;
    }
    
    public String getRole() {
        return "Administrator";
    }
}
